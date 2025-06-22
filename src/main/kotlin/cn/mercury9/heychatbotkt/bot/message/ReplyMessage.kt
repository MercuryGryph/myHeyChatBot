package cn.mercury9.heychatbotkt.bot.message

import cn.mercury9.heychatbotkt.bot.message.card.Cards
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ReplyMessage(
    val msg: String,

    @SerialName("msg_type")
    val msgType: Int,

    @SerialName("room_id")
    val roomId: String,

    @SerialName("channel_id")
    val channelId: String,

    /**
     * Should NOT repeat in 60 seconds.
     */
    @SerialName("heychat_ack_id")
    var heychatAckId: String? = null,

    @SerialName("reply_id")
    val replyId: String? = null,

    @SerialName("channel_type")
    val channelType: Int? = null,

    val addition: String? = "",

    /**
     * List in string, split by `,`
     */
    @SerialName("at_user_id")
    val atUserId: String = "",

    /**
     * List in string, split by `,`
     */
    @SerialName("at_role_id")
    val atRoleId: String = "",

    /**
     * List in string, split by `,`
     */
    @SerialName("mention_channel_id")
    val mentionChannelId: String = "",
) {
    constructor(
        msg: String,
        roomId: String,
        channelId: String,
        msgType: MessageType = MessageType.Markdown,
        heychatAckId: String? = null,
        replyId: String? = null,
        channelType: Int? = null,
        addition: String? = "",
        atUserId: String = "",
        atRoleId: String = "",
        mentionChannelId: String = "",
    ) : this(
        msg = msg,
        msgType = msgType.id,
        roomId = roomId,
        channelId = channelId,
        heychatAckId = heychatAckId,
        replyId = replyId,
        channelType = channelType,
        addition = addition,
        atUserId = atUserId,
        atRoleId = atRoleId,
        mentionChannelId = mentionChannelId,
    )
    constructor(
        msg: Cards,
        roomId: String,
        channelId: String,
        heychatAckId: String? = null,
        replyId: String? = null,
        channelType: Int? = null,
        addition: String? = "",
        atUserId: String = "",
        atRoleId: String = "",
        mentionChannelId: String = "",
    ) : this(
        msg = Json.encodeToString(Cards.serializer(), msg),
        msgType = MessageType.Card,
        roomId = roomId,
        channelId = channelId,
        heychatAckId = heychatAckId,
        replyId = replyId,
        channelType = channelType,
        addition = addition,
        atUserId = atUserId,
        atRoleId = atRoleId,
        mentionChannelId = mentionChannelId,
    )
    constructor(
        roomId: String,
        channelId: String,
        heychatAckId: String? = null,
        replyId: String? = null,
        channelType: Int? = null,
        addition: String? = "",
        atUserId: String = "",
        atRoleId: String = "",
        mentionChannelId: String = "",
        msg: ()->Cards,
    ) : this(
        msg = Json.encodeToString(Cards.serializer(), msg()),
        msgType = MessageType.Card,
        roomId = roomId,
        channelId = channelId,
        heychatAckId = heychatAckId,
        replyId = replyId,
        channelType = channelType,
        addition = addition,
        atUserId = atUserId,
        atRoleId = atRoleId,
        mentionChannelId = mentionChannelId,
    )
}
