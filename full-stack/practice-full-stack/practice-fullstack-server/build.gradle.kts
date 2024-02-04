import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version="2.3.7"
val kotlin_version="1.9.22"
val logback_version="1.4.14"
val koin_version="3.5.3"

plugins {
	kotlin("jvm") version "1.9.22"
	id("io.ktor.plugin") version "2.3.7"
}

group = "jp.glory"
version = "0.0.1"

application {
	mainClass.set("jp.glory.practice.fullstack.server.PracticeFullstackServerApplicationKt")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm")
	implementation("io.ktor:ktor-server-auth-jvm")
	implementation("io.ktor:ktor-server-netty-jvm")
	implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
	implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
	implementation("io.ktor:ktor-server-status-pages:$ktor_version")
	implementation("ch.qos.logback:logback-classic:$logback_version")

	implementation("io.insert-koin:koin-core:$koin_version")
	implementation("io.insert-koin:koin-ktor:$koin_version")
	implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")

	testImplementation("io.ktor:ktor-server-tests-jvm")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}