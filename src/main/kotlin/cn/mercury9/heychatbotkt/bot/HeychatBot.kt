package cn.mercury9.heychatbotkt.bot

import cn.mercury9.heychatbotkt.bot.config.HeychatBotConfig
import cn.mercury9.heychatbotkt.bot.config.HeychatBotConfigger
import cn.mercury9.heychatbotkt.bot.message.Message
import cn.mercury9.heychatbotkt.bot.receive.ReceivedMessage
import cn.mercury9.heychatbotkt.bot.receive.ReceivedBotCommand
import cn.mercury9.heychatbotkt.bot.receive.ReceivedCommonMessage
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.pingInterval
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

@HaychatBotDsl
fun HeychatBot(
    token: String,
    id: String,
    block: HeychatBotConfigger.()-> Unit = {}
): HeychatBot {
    val configger = HeychatBotConfigger()
    configger.apply(block)
    return HeychatBot(
        token = token,
        id = id,
        receivedMessageHandler = configger.receivedMessageHandlers,
        config = configger.build(),
        logger = configger.logger,
    )
}

class HeychatBot(
    val token: String,
    val id: String,
    val receivedMessageHandler: List<ReceivedMessageHandler> = emptyList(),
    val config: HeychatBotConfig = HeychatBotConfig(),
    logger: KLogger? = null,
) {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = false
    }

    private val client: HttpClient = HttpClient(CIO) {
        install(WebSockets) {
            pingInterval = 20.seconds
        }
    }

    private var stopInThisTurn: Boolean = false

    private val logger: KLogger = logger ?: KotlinLogging.logger {}

    private val wsPath = Constants.WS_PATH + "?" +
            config.wsParam.toPathParamStr() +
            "&token=$token"

    suspend fun sendMessage(msg: Message) {
        withContext(Dispatchers.IO) {
            val response = client.post(
                "https://${Constants.HOST}/${Constants.SEND_PATH}?${config.wsParam.toPathParamStr()}"
            ) {
                headers {
                    append("Content-Type", "application/json;charset=UTF-8")
                    append("token", token)
                }
                val body = json.encodeToString(Message.serializer(), msg)
                logger.debug { "Sending message:\n$body" }
                setBody(body)
            }
            if (response.status.isSuccess()) {
                val body = response.bodyAsText()
                logger.info { body }
            }
        }
    }

    suspend fun start() {
        logger.info { "Starting bot..." }

        try {
            while (true) {
                runWebSocket()

                logger.info { "Connection closed." }
                logger.info { "Reconnect in ${config.reconnectDuration}..." }

                if (stopInThisTurn) {
                    break
                }

                delay(config.reconnectDuration)
            }

        } catch (e: Exception) {
            logger.error(e) { "Error during web socket connection." }

        } finally {
            client.close()
            logger.info { "Ending bot..." }
        }
    }

    private suspend fun runWebSocket() {
        client.webSocket(
            host = Constants.HOST,
            path = wsPath,
        ) {
            logger.info { "WebSocket connected." }

            while (true) {
                val frame = incoming.receive()
                if (frame is Frame.Close) break

                when (frame) {
                    is Frame.Text -> launch(Dispatchers.Default) {
                        handelWsIncomingText(frame)
                    }
                    is Frame.Binary -> logger.warn { "Unhandled frame received, frame type: Binary, frame:\n$frame" }
                    is Frame.Ping -> logger.warn { "Unhandled frame received, frame type: Ping, frame:\n$frame" }
                    is Frame.Pong -> logger.warn { "Unhandled frame received, frame type: Pong, frame:\n$frame" }
                    else -> {}
                }
            }
        }
    }

    private suspend fun handelWsIncomingText(incoming: Frame.Text) {
        val raw = incoming.readText()
        logger.debug { "Incoming text at ws, row:\n${raw}" }
        val receivedMessage: ReceivedMessage
        try {
            receivedMessage = json.decodeFromString(ReceivedMessage.serializer(), raw)
        } catch (e: Exception) {
            logger.error(e) { "Error during decode message."  }
            return
        }
        logger.debug { "Decoded" }

        if (receivedMessage is ReceivedBotCommand) {
            if (receivedMessage.data.botId.toString() != id) {
                logger.info { "Bot ID not match, skip. (config: $id, received: ${receivedMessage.data.botId})" }
            }

            logger.info { "Handel received BotCommand" }
            receivedMessageHandler.forEach { handler ->
                val reply = handler.handleBotCommand(receivedMessage)
                reply?.let { sendMessage(it) }
            }

        } else if (receivedMessage is ReceivedCommonMessage) {
            logger.info { "Handel received CommonMessage" }
            val botCommand = receivedMessage.toReceivedBotCommand()
            if (botCommand != null) {
                return
            }
            receivedMessageHandler.forEach { handler ->
                val reply = handler.handleCommonMessage(receivedMessage)
                reply?.let { sendMessage(it) }
            }
        }
    }

    fun stop() {
        logger.info { "Stopping..." }
        stopInThisTurn = true
    }
}
