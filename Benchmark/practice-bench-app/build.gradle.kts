import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("me.champeau.gradle.jmh") version "0.5.2"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
}
apply(plugin ="me.champeau.gradle.jmh")

val jmhTargetVersion = "1.27"
group = "jp.glory"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:$jmhTargetVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	jmh("org.openjdk.jmh:jmh-core:$jmhTargetVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jmh {
	jmhVersion = jmhTargetVersion
}

tasks {
	jmhJar {
		exclude("META-INF/versions/9/module-info.class")
	}
}