package cn.mercury9.heychatbotkt.bot

import cn.mercury9.heychatbotkt.bot.message.Message
import cn.mercury9.heychatbotkt.bot.receive.ReceivedBotCommand
import cn.mercury9.heychatbotkt.bot.receive.ReceivedCommonMessage

interface ReceivedMessageHandler {
    fun handleBotCommand(receivedMessage: ReceivedBotCommand): Message? = null
    fun handleCommonMessage(receivedMessage: ReceivedCommonMessage): Message? = null
}
