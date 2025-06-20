package cn.mercury9.heychatbotkt.bot.receive

import cn.mercury9.heychatbotkt.bot.channel.ChannelBaseInfo
import cn.mercury9.heychatbotkt.bot.room.RoomBaseInfo
import cn.mercury9.heychatbotkt.bot.user.UserInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ReceivedCommonData(
    val addition: String,

    val avatar: String,

    @SerialName("channel_id")
    val channelId: String,

    @SerialName("channel_name")
    val channelName: String,

    @SerialName("channel_type")
    val channelType: Int,

    val msg: String,

    @SerialName("msg_id")
    val msgId: String,

    @SerialName("user_info")
    val userInfo: UserInfo,

    @SerialName("room_id")
    val roomId: String,

    @SerialName("send_time")
    val sendTimeMs: Long,
) {
    fun toReceivedBotCommandData(): ReceivedBotCommandData? {
        if (addition.contains("bot_command")) {
            val json = Json {
                ignoreUnknownKeys = true
            }
            val add = json.decodeFromString(ReceivedCommonDataAddition.serializer(), addition)
            if (add.botCommand != null) {
                return ReceivedBotCommandData(
                    msg = msg,
                    msgId = msgId,
                    commandInfo = add.botCommand.commandInfo,
                    sender = userInfo.userBaseInfo,
                    channelBaseInfo = ChannelBaseInfo(
                        channelId = channelId,
                        channelType = channelType,
                    ),
                    roomBaseInfo = RoomBaseInfo(
                        roomId = roomId,
                    ),
                    sendTimeMs = sendTimeMs,
                )
            }
        }
        return null
    }
}
