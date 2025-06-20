package cn.mercury9.myheychatbot

import cn.mercury9.heychatbotkt.bot.ReceivedMessageHandler
import cn.mercury9.heychatbotkt.bot.command.CommandOption
import cn.mercury9.heychatbotkt.bot.ellipsis
import cn.mercury9.heychatbotkt.bot.message.Message
import cn.mercury9.heychatbotkt.bot.message.card.Card
import cn.mercury9.heychatbotkt.bot.message.card.CardSize
import cn.mercury9.heychatbotkt.bot.message.card.builder.divider
import cn.mercury9.heychatbotkt.bot.message.card.builder.header
import cn.mercury9.heychatbotkt.bot.message.card.builder.section
import cn.mercury9.heychatbotkt.bot.message.card.builder.text
import cn.mercury9.heychatbotkt.bot.receive.ReceivedBotCommand
import io.github.oshai.kotlinlogging.KotlinLogging
import io.koalaql.markout.md.markdown
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class CommandHandler: ReceivedMessageHandler {
    val logger = KotlinLogging.logger {}
    val initTime = Date()

    override fun handleBotCommand(receivedMessage: ReceivedBotCommand): Message? {
        val name = receivedMessage.data.commandInfo.name
        val args: MutableMap<String, CommandOption> = mutableMapOf()

        if (receivedMessage.data.commandInfo.options != null) {
            for (option in receivedMessage.data.commandInfo.options) {
                args[option.name] = option
            }
        }

        when (name) {
            "/ping" -> return commandPing(receivedMessage, args)
            "/r" -> return commandRand(receivedMessage, args)
        }

        return null
    }

    fun commandPing(received: ReceivedBotCommand, args: Map<String, CommandOption>): Message? {
        logger.info { "Handle command: ping" }

        val payload = args["payload"]?.value

        val msg = Card(size = CardSize.Small) {
            header { +"PONG" }


            if (payload != null) {
                divider { "payload" }
                section { +payload }
            }

            divider { "info" }

            section {
                +markdown {
                    table {
                        th {
                            td("")
                            td("id")
                            td("name")
                        }
                        tr {
                            td("room")
                            td(received.data.roomBaseInfo.roomId)
                            td(received.data.roomBaseInfo.roomName?:"")
                        }
                        tr {
                            td("channel")
                            td(received.data.channelBaseInfo.channelId)
                            td(received.data.channelBaseInfo.channelName?:"")
                        }
                        tr {
                            td("sender")
                            td(received.data.sender.userId.toString())
                            td(received.data.sender.nickname?:"")
                        }
                        tr {
                            td("message")
                            td(received.data.msgId)
                            td { c(received.data.msg.ellipsis(20)) }
                        }
                    }
                }
            }
        }

        return Message(
            msg = msg,
            roomId = received.data.roomBaseInfo.roomId,
            channelId = received.data.channelBaseInfo.channelId,
            replyId = received.data.msgId,
        )
    }

    fun commandRand(received: ReceivedBotCommand, args: Map<String, CommandOption>): Message? {
        logger.info { "Handle command: rand" }

        val min = max(0, args["min"]?.value?.toInt() ?: 1)
        val max = min(256, args["max"]?.value?.toInt() ?: 100)
        val num = max(1, args["num"]?.value?.toInt() ?: 1)
        val calcSum = args["sum"]?.value?.lowercase() != "false"

        val res: MutableList<Int> = mutableListOf()

        val rng = Random(Date().time)

        for (i in 1..num) {
            res += rng.nextInt(min, max)
        }

        val msg = Card(size = CardSize.Small) {
            header { +"随机数" }
            section {
                text {
                    - "范围: `[$min, $max]`"
                    - "数量: `$num`"
                }
                if (calcSum) {
                    text { +"总和: `${res.sum()}`" }
                }
            }
            section {
                +markdown {
                    ul {
                        res.forEach {
                            li { +"$it" }
                        }
                    }
                }
            }
        }

        return Message(
            msg = msg,
            roomId = received.data.roomBaseInfo.roomId,
            channelId = received.data.channelBaseInfo.channelId,
            replyId = received.data.msgId,
        )
    }
}
