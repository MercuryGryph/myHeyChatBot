package cn.mercury9.heychatbotkt.bot.room

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomBaseInfo(
    @SerialName("room_avatar")
    val roomAvatarUrl: String? = null,

    @SerialName("room_id")
    val roomId: String,

    @SerialName("room_name")
    val roomName: String? = null,
)
