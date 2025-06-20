package cn.mercury9.heychatbotkt.bot.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("user_base_info")
    val userBaseInfo: UserBaseInfo
)
