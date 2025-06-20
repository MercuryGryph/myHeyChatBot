package cn.mercury9.heychatbotkt.bot.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    @SerialName("bg_color")
    val bgColor: String,

    @SerialName("textColor")
    val textColor: String,

    val text: String,
)
