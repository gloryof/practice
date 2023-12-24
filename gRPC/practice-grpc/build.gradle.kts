import com.google.protobuf.gradle.*
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
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.lognet:grpc-spring-boot-starter:5.1.5")

	// gRRC
	api("io.grpc:grpc-stub:$grpcVersion")
	api("io.grpc:grpc-protobuf:$grpcVersion")
	api("io.grpc:grpc-services:$grpcVersion")
	api("com.google.protobuf:protobuf-java-util:$protobufVersion")
	api("com.google.protobuf:protobuf-kotlin:$protobufVersion")
	api("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
	implementation("io.grpc:grpc-protobuf:$grpcVersion")
	implementation("io.grpc:grpc-stub:$grpcVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
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

