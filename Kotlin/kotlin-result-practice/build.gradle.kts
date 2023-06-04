import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val junitVersion = "5.9.3"

plugins {
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
}

group = "jp.glory"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.18")
	testImplementation(platform("org.junit:junit-bom:5.9.3"))
	testImplementation("org.junit.jupiter:junit-jupiter")
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
