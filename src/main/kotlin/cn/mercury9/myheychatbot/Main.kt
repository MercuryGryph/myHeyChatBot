package cn.mercury9.myheychatbot

import cn.mercury9.heychatbotkt.bot.HeychatBot
import cn.mercury9.myheychatbot.data.loadConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.time.Duration.Companion.seconds

val Logger = KotlinLogging.logger("MyHeychatBot")

suspend fun main() {
    val config = loadConfig()
    val handler = CommandHandler()

    val bot = HeychatBot(config.token, config.id) {
        reconnectDuration { 1.seconds }
        addHandler { handler }
    }

    Logger.info { "Bot initialized." }

    bot.start()
}
