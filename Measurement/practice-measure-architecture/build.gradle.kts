import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.distsDirectory
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.sonarqube") version "4.4.1.3373"
	kotlin("jvm") version "1.9.10"
	kotlin("plugin.spring") version "1.8.22"
	jacoco
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.18")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
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

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}

sonar {
	val token = System.getenv("SONAR_TOKEN")
	properties {
		property("sonar.projectKey", "practice-measure-architecture")
		property("sonar.host.url", "http://localhost:9000")
		// You need token when execute sonar task using sonar.token JVM parameter.
		property("sonar.token", token)
		property("sonar.java.coveragePlugin", "jacoco")
		property("sonar.jacoco.reportPath", "${project.distsDirectory}/jacoco/test.exec")
	}
}
