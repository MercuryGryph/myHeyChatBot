package cn.mercury9.heychatbotkt.bot.receive

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ReceivedMessage

@Serializable
@SerialName("50")
data class ReceivedBotCommand(
    val sequence: Long,

    @SerialName("timestamp")
    val timeMs: Long,

    val data: ReceivedBotCommandData,
): ReceivedMessage()

@Serializable
@SerialName("5")
data class ReceivedCommonMessage(
    val sequence: Long,

    @SerialName("timestamp")
    val timeMs: Long,

    val data: ReceivedCommonData
): ReceivedMessage() {
    fun toReceivedBotCommand(): ReceivedBotCommand? {
        val commandData = data.toReceivedBotCommandData()
        if (commandData == null) return null
        return ReceivedBotCommand(
            sequence = sequence,
            timeMs = timeMs,
            data = commandData,
        )
    }
}
