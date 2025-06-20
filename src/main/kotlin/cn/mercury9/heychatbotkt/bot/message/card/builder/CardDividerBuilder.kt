package cn.mercury9.heychatbotkt.bot.message.card.builder

import cn.mercury9.heychatbotkt.bot.message.card.CardDividerModule
import cn.mercury9.heychatbotkt.bot.message.card.HeyCardDsl

@HeyCardDsl
class CardDividerBuilder(
    val text: String? = null,
) {
    fun build() = CardDividerModule(text)
}

@HeyCardDsl
fun CardDataBuilder.divider(text: String? = null) = append(CardDividerBuilder(text).build())

@HeyCardDsl
fun CardDataBuilder.divider(builder: () -> String) = divider(builder())
