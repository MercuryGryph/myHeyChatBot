package cn.mercury9.heychatbotkt.bot.message.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("divider")
data class CardDividerModule(
    val text: String? = null,
): CardMessageModule()
