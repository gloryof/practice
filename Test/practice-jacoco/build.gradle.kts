val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.0"
}

group = "jp.glory"
version = "0.0.1"
application {
    mainClass.set("jp.glory.app.coverage.practice.PracticeApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson-jvm:$ktorVersion")

    // DB
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("mysql:mysql-connector-java:8.0.30")

    // Jackson
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")

    // Logback
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Koin
    implementation("io.insert-koin:koin-core:3.2.0")

    // Kotlin Result
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.16")

    // Test
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}