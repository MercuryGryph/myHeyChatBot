package cn.mercury9.heychatbotkt.bot.message.card.builder

import cn.mercury9.heychatbotkt.bot.message.card.CardButtonEventType
import cn.mercury9.heychatbotkt.bot.message.card.CardButtonTheme
import cn.mercury9.heychatbotkt.bot.message.card.CardSectionButton
import cn.mercury9.heychatbotkt.bot.message.card.CardSectionImage
import cn.mercury9.heychatbotkt.bot.message.card.CardSectionMarkdown
import cn.mercury9.heychatbotkt.bot.message.card.CardSectionModule
import cn.mercury9.heychatbotkt.bot.message.card.CardSectionParagraph
import cn.mercury9.heychatbotkt.bot.message.card.CardSectionPlainText
import cn.mercury9.heychatbotkt.bot.message.card.CardTextContentType
import cn.mercury9.heychatbotkt.bot.message.card.HeyCardDsl
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.text.isEmpty

/**
 * Types: `Text(PlainText | Markdown)` | `Button` | `Image`
 *
 * Allows:
 * - { `Text` }
 * - { `Text`, `Text`, `Text`, `Text`, `Text`} __(Amount of `Text` can be 1..5)__
 * - { `Text`, `Image` }
 * - { `Image`, `Text` }
 * - { `Text`, `Button` }
 */
@HeyCardDsl
class CardSectionBuilder {
    private val paragraphs = mutableListOf<CardSectionParagraph>()

    fun append(paragraph: CardSectionParagraph) = this.paragraphs.add(paragraph)
    fun append(vararg paragraphs: CardSectionParagraph) = this.paragraphs.addAll(paragraphs)

    operator fun String.unaryPlus() {
        val lastPara = paragraphs.lastOrNull()
        when (lastPara) {
            is CardSectionPlainText ->{
                paragraphs[paragraphs.lastIndex] = lastPara.copy(text = lastPara.text + this)
            }
            is CardSectionMarkdown -> {
                paragraphs[paragraphs.lastIndex] = lastPara.copy(text = lastPara.text + this)
            }
            else -> {
                append(CardSectionMarkdown(this))
            }
        }
    }

    operator fun String.unaryMinus() {
        val lastPara = paragraphs.lastOrNull()
        when (lastPara) {
            is CardSectionPlainText -> {
                paragraphs[paragraphs.lastIndex] =
                    lastPara.copy(
                        text = lastPara.text
                                + if (lastPara.text.isEmpty()) "" else "\n"
                                + this
                    )
            }
            is CardSectionMarkdown -> {
                paragraphs[paragraphs.lastIndex] =
                    lastPara.copy(
                        text = lastPara.text
                                + if (lastPara.text.isEmpty()) "" else "\n\n"
                                + this
                    )
            }
            else -> {
                append(CardSectionMarkdown(this))
            }
        }
    }

    fun build(): CardSectionModule = CardSectionModule(paragraphs)
}

/**
 * Types: `Text(PlainText | Markdown)` | `Button` | `Image`
 *
 * Allows:
 * - { `Text` }
 * - { `Text`, `Text`, `Text`, `Text`, `Text`} __(Amount of `Text` can be 1..5)__
 * - { `Text`, `Image` }
 * - { `Image`, `Text` }
 * - { `Text`, `Button` }
 */
@HeyCardDsl
fun CardDataBuilder.section(
    builder: CardSectionBuilder.() -> Unit
) = append(CardSectionBuilder().apply(builder).build())

@HeyCardDsl
class SectionTextBuilder(
    var text: String = "",
    var width: String = "",
    val type: CardTextContentType = CardTextContentType.Markdown,
) {
    operator fun String.unaryPlus() {
        text += this
    }
    operator fun String.unaryMinus() {
        text += when(type) {
            CardTextContentType.PalinText ->
                "${if (text.isEmpty()) "" else "\n"}$this"
            CardTextContentType.Markdown ->
                "${if (text.isEmpty()) "" else "\n\n"}$this"
        }
    }

    fun build(): CardSectionParagraph {
        var w: String
        try {
            width.removeSuffix("%").toDouble()
            w = width
        } catch (e: NumberFormatException) {
            w = ""
            KotlinLogging.logger {}.warn(e) { "Illegal width of card section text: `$width`. Must be a number or percentage." }
        }
        return when (type) {
            CardTextContentType.PalinText -> CardSectionPlainText(text, width)
            CardTextContentType.Markdown -> CardSectionMarkdown(text, width)
        }
    }
}

@HeyCardDsl
fun CardSectionBuilder.text(
    width: String = "",
    type: CardTextContentType = CardTextContentType.Markdown,
    builder: SectionTextBuilder.() -> Unit
) = append(SectionTextBuilder(width = width, type = type).apply(builder).build())

@HeyCardDsl
class SectionButtonBuilder(
    var text: String = "",
    var evevt: CardButtonEventType = CardButtonEventType.Link,
    var value: String = "",
    var theme: CardButtonTheme = CardButtonTheme.Default
) {
    operator fun String.unaryPlus() {
        text += this
    }
    operator fun String.unaryMinus() {
        text += "${if (text.isEmpty()) "" else "\n"}$this"
    }
}
