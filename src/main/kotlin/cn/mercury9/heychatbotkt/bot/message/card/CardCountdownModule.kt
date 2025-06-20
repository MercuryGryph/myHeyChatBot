package cn.mercury9.heychatbotkt.bot.message.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("countdown")
data class CardCountdownModule(
    @SerialName("end_time")
    val endTimeSec: Long,
    val mode: CardCountdownMode,
): CardMessageModule()

@Serializable
enum class CardCountdownMode {
    @SerialName("default")
    Default,
    @SerialName("second")
    Second,
    @SerialName("calendar")
    Calendar,
}
