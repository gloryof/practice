val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val jUnitVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.0"
    id("jacoco")
}

group = "jp.glory"
version = "0.0.1"
application {
    mainClass.set("jp.glory.app.coverage.practice.PracticeApplicationKt")

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

    // DB
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("mysql:mysql-connector-java:8.0.30")

    // Jackson
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")

    // Logback
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Koin
    implementation("io.insert-koin:koin-core:3.2.0")

    // Kotlin Result
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.16")

    // Test
    testImplementation(platform("org.junit:junit-bom:$jUnitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
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

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "BUNDLE" // Default
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }

        // これの範囲がわからない
        rule {
            element = "SOURCEFILE"
            includes = listOf("*.domain.*")
            limit {
                minimum = "0.6".toBigDecimal()
            }
        }

        // これの範囲がわからない
        rule {
            element = "GROUP"
            includes = listOf("*.domain.*")
            limit {
                minimum = "0.7".toBigDecimal()
            }
        }

        rule {
            element = "METHOD"
            includes = listOf("*.domain.*")
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }

        rule {
            element = "CLASS"
            includes = listOf("*.domain.*")
            limit {
                minimum = "0.9".toBigDecimal()
            }
        }

        rule {
            element = "PACKAGE"
            includes = listOf("*.domain.*")
            limit {
                minimum = "1".toBigDecimal()
            }
        }
    }
}