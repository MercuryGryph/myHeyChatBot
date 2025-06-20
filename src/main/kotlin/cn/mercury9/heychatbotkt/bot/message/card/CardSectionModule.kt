@file:Suppress("Unused")

package cn.mercury9.heychatbotkt.bot.message.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("section")
data class CardSectionModule(
    /**
     * max length: 5
     */
    val paragraph: List<CardSectionParagraph>,
): CardMessageModule()

@Serializable
sealed class CardSectionParagraph

@Serializable
@SerialName("plain-text")
data class CardSectionPlainText(
    val text: String,
    val width: String = "",
): CardSectionParagraph()

@Serializable
@SerialName("markdown")
data class CardSectionMarkdown(
    val text: String,
    val width: String = "",
): CardSectionParagraph()

@Serializable
@SerialName("button")
data class CardSectionButton(
    val text: String,
    val event: CardButtonEventType,
    val value: String,
    val theme: CardButtonTheme,
): CardSectionParagraph()

@Serializable
@SerialName("image")
data class CardSectionImage(
    val url: String,
    val size: CardImageSize,
): CardSectionParagraph()
