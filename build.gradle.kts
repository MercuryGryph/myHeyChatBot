plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "cn.mercury9"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(libs.logback)
    implementation(libs.logging)

    implementation(libs.bundles.ktor.client)
    implementation(libs.kotlin.serialization.json)

    implementation(libs.markout)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
