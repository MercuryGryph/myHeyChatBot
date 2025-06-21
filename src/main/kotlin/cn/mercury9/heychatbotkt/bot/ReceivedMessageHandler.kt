package cn.mercury9.heychatbotkt.bot

import cn.mercury9.heychatbotkt.bot.command.CommandOption
import cn.mercury9.heychatbotkt.bot.message.Message
import cn.mercury9.heychatbotkt.bot.receive.ReceivedBotCommand
import cn.mercury9.heychatbotkt.bot.receive.ReceivedCommonMessage

interface ReceivedMessageHandler {
    fun handleBotCommand(receivedMessage: ReceivedBotCommand): Message? = null
    fun handleCommonMessage(receivedMessage: ReceivedCommonMessage): Message? = null
}

@DslMarker
annotation class MessageHandlerDsl

@MessageHandlerDsl
class Handler: ReceivedMessageHandler {
    private val commandHandlers
        = mutableMapOf<String, (received: ReceivedBotCommand, args: Map<String, CommandOption>)->Message?>()

    private var messageHandlers: (received: ReceivedCommonMessage)->Message? = { null }

    override fun handleBotCommand(receivedMessage: ReceivedBotCommand): Message? {
        val name = receivedMessage.data.commandInfo.name
        val args: MutableMap<String, CommandOption> = mutableMapOf()

        if (receivedMessage.data.commandInfo.options != null) {
            for (option in receivedMessage.data.commandInfo.options) {
                args[option.name] = option
            }
        }

        return commandHandlers[name]?.invoke(receivedMessage, args)
    }

    override fun handleCommonMessage(receivedMessage: ReceivedCommonMessage): Message? = messageHandlers(receivedMessage)

    fun message(
        handler: (received: ReceivedCommonMessage)->Message?
    ) {
        messageHandlers = handler
    }

    fun command(
        name: String,
        handler: (received: ReceivedBotCommand, args: Map<String, CommandOption>)->Message?
    ) {
        commandHandlers[name] = handler
    }
}

@MessageHandlerDsl
fun Handler(
    builder: Handler.() -> Unit
): Handler = Handler().apply(builder)
