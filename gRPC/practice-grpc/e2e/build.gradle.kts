import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val protobufVersion = "3.23.4"
val protobufPluginVersion = "0.8.18"
val grpcVersion = "1.58.0"
val grpcKotlinVersion = "1.4.1"

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.google.protobuf") version "0.8.18"
	id("io.github.lognet.grpc-spring-boot") version "5.1.5"

	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
}

group = "jp.glory"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(project(":app"))
	testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

