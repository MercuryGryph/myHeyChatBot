package cn.mercury9.heychatbotkt.bot.config

import cn.mercury9.heychatbotkt.bot.HaychatBotDsl

@Suppress("Unused")
@HaychatBotDsl
class HeychatBotUrlParamConfiger {
    internal constructor()

    var clientType: () -> String = { "heybox_chat" }
        private set
    fun clientType(block: () -> String) {
        clientType = block
    }

    var xClientType: () -> String = { "web" }
        private set
    fun xClientType(block: () -> String) {
        xClientType = block
    }

    var osType: () -> String = { "web" }
        private set
    fun osType(block: () -> String) {
        osType = block
    }

    var xOsType: () -> String = { "bot" }
        private set
    fun xOsType(block: () -> String) {
        xOsType = block
    }

    var xApp: () -> String = { "heybox_chat" }
        private set
    fun xApp(block: () -> String) {
        xApp = block
    }

    var chatOsType: () -> String = { "bot" }
        private set
    fun chatOsType(block: () -> String) {
        chatOsType = block
    }

    var chatVersion: () -> String = { "1.30.0" }
        private set
    fun chatVersion(block: () -> String) {
        chatVersion = block
    }

    fun build() = HeychatBotUrlParam(
        clientType(),
        xClientType(),
        osType(),
        xOsType(),
        xApp(),
        chatOsType(),
        chatVersion(),
    )
}
