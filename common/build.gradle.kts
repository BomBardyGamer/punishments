import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.4.21"
}

group = "com.prevarinite.punishments"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

object Versions {
    const val EXPOSED = "0.27.1"
    const val KOIN = "2.1.6"
}

dependencies {

    implementation(project(":api"))

    implementation("org.jetbrains.kotlinx","kotlinx-serialization-runtime","1.0-M1-1.4.0-rc")
    implementation("com.squareup.retrofit2","retrofit", "2.9.0")
    implementation("com.jakewharton.retrofit","retrofit2-kotlinx-serialization-converter","0.6.0")
    implementation("com.squareup.okhttp3","okhttp","4.8.1")

    implementation("org.koin","koin-core", Versions.KOIN)

    implementation("org.jetbrains.exposed","exposed-core", Versions.EXPOSED)
    implementation("org.jetbrains.exposed","exposed-dao", Versions.EXPOSED)
    implementation("org.jetbrains.exposed","exposed-jdbc", Versions.EXPOSED)
    implementation("org.jetbrains.exposed","exposed-java-time", Versions.EXPOSED)

    implementation("net.md-5","bungeecord-chat","1.16-R0.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xjsr305=strict",
            "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
            "-Xopt-in=kotlin.time.ExperimentalTime"
        )
    }
}
