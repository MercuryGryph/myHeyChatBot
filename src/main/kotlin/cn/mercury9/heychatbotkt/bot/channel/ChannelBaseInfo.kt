package cn.mercury9.heychatbotkt.bot.channel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChannelBaseInfo(
    @SerialName("channel_name")
    val channelName: String? = null,

    @SerialName("channel_id")
    val channelId: String,

    @SerialName("channel_type")
    val channelType: Int,
)
