package cn.mercury9.heychatbotkt.bot.message.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("images")
data class CardImagesModule(
    /**
     * max length: 18
     */
    val urls: List<CardImageUrl>,
): CardMessageModule()

@Suppress("Unused")
@Serializable
enum class CardImageSize {
    @SerialName("small")
    Small,
    @SerialName("medium")
    Medium,
    @SerialName("large")
    Large,
}

@Serializable
data class CardImageUrl(
    val url: String,
)
