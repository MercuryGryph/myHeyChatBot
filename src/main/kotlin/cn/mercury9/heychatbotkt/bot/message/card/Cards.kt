package cn.mercury9.heychatbotkt.bot.message.card

import cn.mercury9.heychatbotkt.bot.message.card.builder.CardDataBuilder
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cards(
    val data: List<CardData>
)

@HeyCardDsl
fun Cards(builder: CardListBuilder.() -> Unit): Cards = CardListBuilder().apply(builder).build()

@HeyCardDsl
fun Card(
    borderColor: String = "",
    size: CardSize = CardSize.Medium,
    type: CardType = CardType.Card,
    builder: CardDataBuilder.() -> Unit
): Cards = Cards {
    card(borderColor, size, type, builder)
}

@HeyCardDsl
class CardListBuilder {

    private val data = mutableListOf<CardData>()

    fun card(
        borderColor: String = "",
        size: CardSize = CardSize.Medium,
        type: CardType = CardType.Card,
        builder: CardDataBuilder.() -> Unit
    ) {
        data += CardDataBuilder(
            borderColor = borderColor,
            size = size,
            type = type,
        ).apply(builder).build()
    }

    fun build() = Cards(data)
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class CardData(
    val modules: MutableList<CardMessageModule>,

    @SerialName("border_color")
    var borderColor: String = "",

    var size: CardSize = CardSize.Medium,

    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    var type: CardType = CardType.Card,
)

@Serializable
enum class CardType {
    @SerialName("card")
    Card,
}

@Suppress("Unused")
@Serializable
enum class CardSize {
    @SerialName("small")
    Small,
    @SerialName("medium")
    Medium,
    @SerialName("large")
    Large,
}

