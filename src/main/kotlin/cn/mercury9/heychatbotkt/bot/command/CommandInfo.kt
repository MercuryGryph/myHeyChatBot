package cn.mercury9.heychatbotkt.bot.command

import kotlinx.serialization.Serializable

@Serializable
data class CommandInfo(
    val id: String,
    val name: String,
    val options: List<CommandOption>? = null,
    val type: Int,
)
