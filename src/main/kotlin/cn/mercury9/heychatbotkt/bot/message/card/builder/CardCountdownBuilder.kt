@file:Suppress("Unused")

package cn.mercury9.heychatbotkt.bot.message.card.builder

import cn.mercury9.heychatbotkt.bot.message.card.CardCountdownMode
import cn.mercury9.heychatbotkt.bot.message.card.CardCountdownModule
import cn.mercury9.heychatbotkt.bot.message.card.HeyCardDsl
import java.util.*

@HeyCardDsl
class CardCountdownBuilder(
    val endTime: Date,
    val mode: CardCountdownMode = CardCountdownMode.Default
) {
    fun build(): CardCountdownModule {
        return CardCountdownModule(
            endTimeSec = endTime.time / 1000,
            mode = mode
        )
    }
}

@HeyCardDsl
fun CardDataBuilder.countdown(
    mode: CardCountdownMode = CardCountdownMode.Default,
    builder: () -> Date
) = append(CardCountdownBuilder(builder(), mode).build())
