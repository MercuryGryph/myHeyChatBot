package cn.mercury9.heychatbotkt.bot.message.card.builder

import cn.mercury9.heychatbotkt.bot.message.card.CardImageUrl
import cn.mercury9.heychatbotkt.bot.message.card.CardImagesModule
import cn.mercury9.heychatbotkt.bot.message.card.HeyCardDsl

@HeyCardDsl
class CardImagesBuilder(
    urls: List<CardImageUrl> = emptyList(),
) {
    val urls = urls.toMutableList()

    fun append(image: CardImageUrl) = urls.add(image)
    fun append(vararg image: CardImageUrl) = urls.addAll(image)

    fun url(url: String) = append(CardImageUrl(url))

    operator fun String.unaryPlus() = url(this)
    operator fun String.unaryMinus() = url(this)

    fun build() = CardImagesModule(urls)
}

@Suppress("Unused")
@HeyCardDsl
fun CardDataBuilder.images(
    builder: CardImagesBuilder.() -> Unit
) = append(CardImagesBuilder().apply(builder).build())
