package cn.mercury9.heychatbotkt.bot.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBaseInfo(
    @SerialName("user_id")
    val userId: Int,

    val nickname: String?,

    val avatar: String? = null,

    @SerialName("avatar_decoration")
    val avatarDecoration: AvatarDecoration? = null,

    @SerialName("bot")
    val isBot: Boolean? = null,

    val level: Int? = null,

    val tag: Tag? = null,

    val medals: Array<Medals> = emptyArray(),
)
