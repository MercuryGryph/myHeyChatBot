package cn.mercury9.myheychatbot.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.system.exitProcess

@Serializable
data class Config(
    val token: String = "***your token here***",
    val id: String = "***your bot ID***",
)

fun loadConfig(): Config {
    val json = Json {
        prettyPrint = true
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
    val file = File("config.json")

    if (!file.exists()) {
        file.createNewFile()
        val config = Config()
        file.writeText(json.encodeToString(Config.serializer(), config))
        println("Please edit the config JSON file.")
        exitProcess(0)
    }
    val config = json.decodeFromString(Config.serializer(), file.readText())
    return config
}
