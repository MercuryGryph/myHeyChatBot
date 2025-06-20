package cn.mercury9.heychatbotkt.bot.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 勋章
 */
@Serializable
data class Medals(
    val name: String,

    @SerialName("name_short")
    val nameShort: String,

    val description: String,

    val color: String,

    @SerialName("img_url")
    val imgUrl: String,

    @SerialName("medal_id")
    val medalId: Int,
)
