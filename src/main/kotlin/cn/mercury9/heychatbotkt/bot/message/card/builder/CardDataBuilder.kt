package cn.mercury9.heychatbotkt.bot.message.card.builder

import cn.mercury9.heychatbotkt.bot.message.card.CardData
import cn.mercury9.heychatbotkt.bot.message.card.CardMessageModule
import cn.mercury9.heychatbotkt.bot.message.card.CardSize
import cn.mercury9.heychatbotkt.bot.message.card.CardType
import cn.mercury9.heychatbotkt.bot.message.card.HeyCardDsl

@Suppress("Unused")
@HeyCardDsl
class CardDataBuilder(
    private var borderColor: String = "",
    private var size: CardSize = CardSize.Medium,
    private var type: CardType = CardType.Card,
) {

    fun type(builder: () -> CardType) {
        type = builder()
    }

    fun borderColor(builder: () -> String) {
        borderColor = builder()
    }

    fun size(builder: () -> CardSize) {
        size = builder()
    }

    private val modules = mutableListOf<CardMessageModule>()

    fun append(vararg module: CardMessageModule) {
        modules += module
    }

    fun build() = CardData(
        modules = modules,
        type = type,
        borderColor = borderColor,
        size = size,
    )
}
