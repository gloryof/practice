import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage


// Workaround https://github.com/spring-projects/spring-boot/issues/33368
buildscript {
	repositories {
		maven(url = "https://repo1.maven.org/maven2")
	}
}

plugins {
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
}

group = "jp.glory"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	// Micrometer
	implementation("io.micrometer:micrometer-tracing-bridge-otel")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")

	// Reactor
	implementation("io.projectreactor:reactor-core-micrometer")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

	// Open Telemetry
	implementation("io.opentelemetry:opentelemetry-exporter-zipkin")
	implementation("com.github.loki4j:loki-logback-appender:1.4.0")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.16")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.security:spring-security-test")

	// Rest assured
	testImplementation("io.rest-assured:rest-assured:5.3.0")
	testImplementation("io.rest-assured:kotlin-extensions:5.3.0")
	testImplementation("io.rest-assured:json-path:5.3.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named<BootBuildImage>("bootBuildImage") {
	environment.set(
		environment.get() +
				mapOf("BP_JVM_VERSION" to "17")
	)
	imageName.set("glory/${project.name}")
}