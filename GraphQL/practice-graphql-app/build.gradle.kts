import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
}

group = "jp.glory"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }  // Spring milestones
    maven { url = uri("https://repo.spring.io/snapshot") }   // Spring snapshots
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.experimental:graphql-spring-boot-starter:1.0.0-SNAPSHOT")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.13")

    // GraphQL
    implementation("com.graphql-java:graphql-java-extended-scalars:17.0")
    implementation("com.graphql-java:graphql-java-extended-validation:17.0-hibernate-validator-6.2.0.Final")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "16"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
