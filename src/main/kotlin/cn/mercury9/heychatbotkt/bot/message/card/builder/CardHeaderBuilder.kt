package cn.mercury9.heychatbotkt.bot.message.card.builder

import cn.mercury9.heychatbotkt.bot.message.card.CardHeaderContent
import cn.mercury9.heychatbotkt.bot.message.card.CardTextContentType
import cn.mercury9.heychatbotkt.bot.message.card.CardHeaderModule
import cn.mercury9.heychatbotkt.bot.message.card.HeyCardDsl

@HeyCardDsl
class CardHeaderBuilder(
    val type: CardTextContentType,
) {
    private var text = ""

    operator fun String.unaryPlus() {
        text += this
    }
    operator fun String.unaryMinus() {
        text += "${if (text.isEmpty()) "" else "\n"}$this"
    }

    fun build() = CardHeaderModule(CardHeaderContent(type, text))
}

@HeyCardDsl
fun CardDataBuilder.header(
    type: CardTextContentType = CardTextContentType.Markdown,
    builder: CardHeaderBuilder.() -> Unit
) = append(CardHeaderBuilder(type).apply(builder).build())
