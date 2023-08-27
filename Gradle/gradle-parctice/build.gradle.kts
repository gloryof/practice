import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	alias(springLib.plugins.boot)
	alias(springLib.plugins.dependency)
	alias(kotlinLib.plugins.jvm)
	alias(kotlinLib.plugins.spring)
}

group = "jp.glory"
version = "0.0.1-SNAPSHOT"

val junitTestSrc = "src/test/junit"
val pbtTestSrc = "src/test/pbt"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation(libs.either)
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(testLib.kotest)
	testImplementation(testLib.kotest.junit)
	testImplementation(testLib.kotest.pbt)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}


testing {
	suites {
		val test by getting(JvmTestSuite::class) {
			useJUnitJupiter()
			sources {
				kotlin {
					srcDir(junitTestSrc)
					srcDir(pbtTestSrc)
				}
			}
		}

		val junitTest by registering(JvmTestSuite::class) {
			useJUnitJupiter()
			dependencies {
				implementation(project())
			}
			sources {
				kotlin {
					srcDir(junitTestSrc)
				}
			}
		}

		val pbtTest by registering(JvmTestSuite::class) {
			useJUnitJupiter()
			dependencies {
				implementation(project())
				implementation(testLib.kotest)
				implementation(testLib.kotest.junit)
				implementation(testLib.kotest.pbt)
			}
			sources {
				kotlin {
					srcDir(pbtTestSrc)
				}
			}
		}

	}
}