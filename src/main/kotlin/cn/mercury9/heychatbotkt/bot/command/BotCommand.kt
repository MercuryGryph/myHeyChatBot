package cn.mercury9.heychatbotkt.bot.command

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BotCommand(
    @SerialName("bot_id")
    val botId: Long,
    @SerialName("command_info")
    val commandInfo: CommandInfo,
)
