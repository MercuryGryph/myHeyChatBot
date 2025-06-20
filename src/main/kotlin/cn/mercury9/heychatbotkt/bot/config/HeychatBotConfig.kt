package cn.mercury9.heychatbotkt.bot.config

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class HeychatBotConfig(
    val reconnectDuration: Duration = 5.seconds,
    val wsParam: HeychatBotUrlParam = HeychatBotUrlParam(),
)
