package cn.mercury9.heychatbotkt.bot.config

import cn.mercury9.heychatbotkt.bot.HaychatBotDsl
import cn.mercury9.heychatbotkt.bot.ReceivedMessageHandler
import io.github.oshai.kotlinlogging.KLogger
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Suppress("Unused")
@HaychatBotDsl
class HeychatBotConfigger {
    internal constructor()

    var logger : KLogger? = null
        private set
    fun logger(block: () -> KLogger) {
        this.logger = block()
    }

    private val wsParamBuilder = HeychatBotUrlParamConfiger()
    fun wsPathParam(block: HeychatBotUrlParamConfiger.() -> Unit) {
        this.wsParamBuilder.block()
    }

    var reconnectDuration: () -> Duration = { 5.seconds }
        private set
    fun reconnectDuration(block: () -> Duration) {
        this.reconnectDuration = block
    }

    internal val receivedMessageHandlers = mutableListOf<ReceivedMessageHandler>()
    fun addHandler(handler: ReceivedMessageHandler) {
        receivedMessageHandlers += handler
    }
    fun addHandler(vararg handlers: ReceivedMessageHandler) {
        receivedMessageHandlers += handlers
    }
    fun addHandler(builder: () -> ReceivedMessageHandler) {
        receivedMessageHandlers += builder()
    }

    fun build(): HeychatBotConfig {
        return HeychatBotConfig(
            reconnectDuration = reconnectDuration(),
            wsParam = wsParamBuilder.build(),
        )
    }
}
