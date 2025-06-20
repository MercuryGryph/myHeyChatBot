package cn.mercury9.heychatbotkt.bot.message.card.builder

import cn.mercury9.heychatbotkt.bot.message.card.CardButton
import cn.mercury9.heychatbotkt.bot.message.card.CardButtonEventType
import cn.mercury9.heychatbotkt.bot.message.card.CardButtonModule
import cn.mercury9.heychatbotkt.bot.message.card.CardButtonTheme
import cn.mercury9.heychatbotkt.bot.message.card.CardButtonType
import cn.mercury9.heychatbotkt.bot.message.card.HeyCardDsl

@HeyCardDsl
class CardButtonGroupBuilder(
    val buttons: MutableList<CardButton> = mutableListOf(),
) {
    fun append(btn: CardButton) = buttons.add(btn)
    fun append(vararg btns: CardButton) = buttons.addAll(btns)


    fun button(
        event: CardButtonEventType,
        value: String,
        text: String,
    ) = append(CardButtonBuilder(text, event, value).build())

    inline fun button(
        event: CardButtonEventType,
        value: String,
        text: () -> String,
    ) = button(event, value, text())

    fun build() = CardButtonModule(buttons)
}

@HeyCardDsl
fun CardDataBuilder.buttons(
    builder: CardButtonGroupBuilder.() -> Unit
) = append(CardButtonGroupBuilder().apply(builder).build())

@HeyCardDsl
class CardButtonBuilder(
    var text: String,
    var event: CardButtonEventType,
    var value: String,
    var theme: CardButtonTheme = CardButtonTheme.Default,
    var type: CardButtonType = CardButtonType.Button,
) {
    fun build() = CardButton(
        type = type,
        text = text,
        event = event,
        theme = theme,
        value = value,
    )
}

@HeyCardDsl
inline fun CardButtonGroupBuilder.link(
    target: String,
    text: ()->String
) = button(
    event = CardButtonEventType.Link,
    value = target,
    text = text,
)

@HeyCardDsl
inline fun CardButtonGroupBuilder.server(
    target: String,
    text: ()->String
) = button(
    event = CardButtonEventType.Server,
    value = target,
    text = text,
)
