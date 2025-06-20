package cn.mercury9.heychatbotkt.bot.config

data class HeychatBotUrlParam(
    var clientType: String = "heybox_chat",
    var xClientType: String = "web",
    var osType: String = "web",
    var xOsType: String = "bot",
    var xApp: String = "heybox_chat",
    var chatOsType: String = "bot",
    var chatVersion: String = "1.30.0",
) {
    fun toPathParamStr(): String =
        "client_type=$clientType" +
        "&x_client_type=$xClientType" +
        "&os_type=$osType" +
        "&x_os_type=$xOsType" +
        "&x_app=$xApp" +
        "&chat_os_type=$chatOsType" +
        "&chat_version=$chatVersion"
}
