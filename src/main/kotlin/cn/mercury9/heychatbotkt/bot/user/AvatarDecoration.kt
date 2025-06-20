package cn.mercury9.heychatbotkt.bot.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarDecoration(
    @SerialName("src_type")
    val srcType: String,

    @SerialName("src_url")
    val srcUrl: String,
)
