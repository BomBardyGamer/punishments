plugins {
    kotlin("jvm")
}

group = "com.prevarinite.punishments"
version = "1.0-SNAPSHOT"

repositories {
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("com.destroystokyo.paper","paper-api","1.16.4-R0.1-SNAPSHOT")

    implementation(project(":common"))
    implementation(project(":api"))
}
