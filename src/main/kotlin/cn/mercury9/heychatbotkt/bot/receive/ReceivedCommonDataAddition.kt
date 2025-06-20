package cn.mercury9.heychatbotkt.bot.receive

import cn.mercury9.heychatbotkt.bot.command.BotCommand
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceivedCommonDataAddition(
    @SerialName("bot_command")
    val botCommand: BotCommand? = null,
)
