package cn.mercury9.heychatbotkt.bot.message.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("button-group")
data class CardButtonModule(
    @SerialName("btns")
    val buttons: List<CardButton>
): CardMessageModule()

@Serializable
data class CardButton(
    val type: CardButtonType,
    val text: String,
    val event: CardButtonEventType,
    val value: String,
    val theme: CardButtonTheme,
)

@Suppress("Unused")
@Serializable
enum class CardButtonTheme {
    @SerialName("default")
    Default,
    @SerialName("success")
    Success,
    @SerialName("primary")
    Primary,
    @SerialName("danger")
    Danger,
}

@Serializable
enum class CardButtonEventType {
    @SerialName("link-to")
    Link,
    @SerialName("server")
    Server,
}

@Serializable
enum class CardButtonType {
    @SerialName("button")
    Button,
}
