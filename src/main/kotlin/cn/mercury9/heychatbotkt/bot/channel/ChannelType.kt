package cn.mercury9.heychatbotkt.bot.channel

import kotlinx.serialization.Serializable

@Serializable
enum class ChannelType {
    /**语音频道**/
    Voice,
    /**文字频道**/
    Text,
    /**公告频道**/
    Announcement,
    /**分组类型的频道**/
    Group,
    /**临时频道**/
    Temp,
    /**临时频道管理器**/
    TempManager
}
