package cn.mercury9.heychatbotkt.bot.command

@Suppress("Unused")
enum class CommandOptionType(val id: Int) {
    String(3),
    Number(4),
    Boolean(5),
    User(6),
    Channel(7),
    Role(8),
    Option(9),
    Integer(10),

    Unknown(-1);

    companion object {
        fun of(id: Int): CommandOptionType = entries.find { it.id == id } ?: Unknown
    }
}