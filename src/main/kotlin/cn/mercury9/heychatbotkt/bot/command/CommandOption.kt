package cn.mercury9.heychatbotkt.bot.command

import kotlinx.serialization.Serializable

@Serializable
data class CommandOption(
    val name: String,
    val type: Int,
    val value: String,
)