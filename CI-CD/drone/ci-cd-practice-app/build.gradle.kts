import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jibTargetHost: String =  System.getenv("JIB_TARGET_HOST") ?: ""

plugins {
	id("org.springframework.boot") version "2.6.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.google.cloud.tools.jib") version "3.1.4"
	id("org.sonarqube") version "3.3"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	jacoco
}

group = "jp.glory"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.5")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.5")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}
tasks.withType<JacocoReport> {
	dependsOn(tasks.test)
	reports {
		html.required.set(true)
		xml.required.set(true)
	}
}

tasks.withType<GradleBuild> {
	dependsOn("jib")
}
jib {
	from {
		image = "openjdk:17.0.1-slim"
	}
	to {
		image = "$jibTargetHost:30500/ci-cd-practice-app"
	}
	container {
		creationTime = "USE_CURRENT_TIMESTAMP"
	}
	setAllowInsecureRegistries(true)
}


sonarqube {
	properties {
		property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
	}
}