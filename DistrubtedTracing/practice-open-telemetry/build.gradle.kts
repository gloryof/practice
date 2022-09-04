val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.0"
}

group = "jp.glory"
version = "0.0.1"
application {
    mainClass.set("jp.glory.app.open_telemetry.practice.PracticeApplicationKt")

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

    // Open Telemetry
    implementation(platform("io.opentelemetry:opentelemetry-bom:1.17.0"))
    implementation("io.opentelemetry:opentelemetry-api")
    implementation("io.opentelemetry:opentelemetry-context")
    implementation("io.opentelemetry:opentelemetry-exporter-logging")
    implementation("io.opentelemetry.instrumentation:opentelemetry-ktor-2.0:1.17.0-alpha")

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