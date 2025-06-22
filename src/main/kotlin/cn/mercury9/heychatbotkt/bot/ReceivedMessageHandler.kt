package cn.mercury9.heychatbotkt.bot

import cn.mercury9.heychatbotkt.bot.command.CommandOption
import cn.mercury9.heychatbotkt.bot.message.ReplyMessage
import cn.mercury9.heychatbotkt.bot.receive.ReceivedBotCommand
import cn.mercury9.heychatbotkt.bot.receive.ReceivedCommonMessage

interface ReceivedMessageHandler {
    fun handleBotCommand(receivedMessage: ReceivedBotCommand): ReplyMessage? = null
    fun handleCommonMessage(receivedMessage: ReceivedCommonMessage): ReplyMessage? = null
}

@DslMarker
annotation class MessageHandlerDsl

@MessageHandlerDsl
class Handler: ReceivedMessageHandler {
    private val commandHandlers
        = mutableMapOf<String, (received: ReceivedBotCommand, args: Map<String, CommandOption>)->ReplyMessage?>()

    private var messageHandlers: (received: ReceivedCommonMessage)->ReplyMessage? = { null }

    override fun handleBotCommand(receivedMessage: ReceivedBotCommand): ReplyMessage? {
        val name = receivedMessage.data.commandInfo.name
        val args: MutableMap<String, CommandOption> = mutableMapOf()

        if (receivedMessage.data.commandInfo.options != null) {
            for (option in receivedMessage.data.commandInfo.options) {
                args[option.name] = option
            }
        }

        return commandHandlers[name]?.invoke(receivedMessage, args)
    }

    override fun handleCommonMessage(receivedMessage: ReceivedCommonMessage): ReplyMessage? = messageHandlers(receivedMessage)

    fun message(
        handler: (received: ReceivedCommonMessage)->ReplyMessage?
    ) {
        messageHandlers = handler
    }

    fun command(
        name: String,
        handler: (received: ReceivedBotCommand, args: Map<String, CommandOption>)->ReplyMessage?
    ) {
        commandHandlers[name] = handler
    }
}

@MessageHandlerDsl
fun Handler(
    builder: Handler.() -> Unit
): Handler = Handler().apply(builder)
