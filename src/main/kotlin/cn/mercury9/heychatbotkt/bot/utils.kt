package cn.mercury9.heychatbotkt.bot

fun String.ellipsis(length: Int): String {
    if (this.length <= length) return this
    return substring(0, length) + "..."
}
