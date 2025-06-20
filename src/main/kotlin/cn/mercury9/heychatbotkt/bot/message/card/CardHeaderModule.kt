package cn.mercury9.heychatbotkt.bot.message.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("header")
data class CardHeaderModule(
    val content: CardHeaderContent,
): CardMessageModule()

@Serializable
data class CardHeaderContent(
    val type: CardTextContentType,
    val text: String,
)

@Serializable
enum class CardTextContentType {
    @SerialName("plain-text")
    PalinText,

    @SerialName("markdown")
    Markdown,
}
