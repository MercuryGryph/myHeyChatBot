package cn.mercury9.heychatbotkt.bot.receive

import cn.mercury9.heychatbotkt.bot.channel.ChannelBaseInfo
import cn.mercury9.heychatbotkt.bot.command.CommandInfo
import cn.mercury9.heychatbotkt.bot.room.RoomBaseInfo
import cn.mercury9.heychatbotkt.bot.user.UserBaseInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceivedBotCommandData(
    @SerialName("bot_id")
    val botId: Int? = null,

    val msg: String,

    @SerialName("msg_id")
    val msgId: String,

    @SerialName("command_info")
    val commandInfo: CommandInfo,

    @SerialName("sender_info")
    val sender: UserBaseInfo,

    @SerialName("channel_base_info")
    val channelBaseInfo: ChannelBaseInfo,

    @SerialName("room_base_info")
    val roomBaseInfo: RoomBaseInfo,

    @SerialName("send_time")
    val sendTimeMs: Long,
)
