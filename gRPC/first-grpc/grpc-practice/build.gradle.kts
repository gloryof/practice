import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val grpcVersion = "1.47.0"
val protobufVersion = "3.21.1"
val grpcKotlinVersion = "1.3.0"
buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.18")
	}
}

plugins {
	id("idea")
	id("java-library")
	id("com.google.protobuf") version "0.8.18"
	kotlin("jvm") version "1.6.21"
}

group = "jp.glory"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	// gRRC
	api("io.grpc:grpc-stub:$grpcVersion")
	api("io.grpc:grpc-protobuf:$grpcVersion")
	api("io.grpc:grpc-services:$grpcVersion")
	api("com.google.protobuf:protobuf-java-util:$protobufVersion")
	api("com.google.protobuf:protobuf-kotlin:$protobufVersion")
	api("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
	runtimeOnly("io.grpc:grpc-netty:$grpcVersion")
	compileOnly("org.apache.tomcat:annotations-api:6.0.53")

	implementation("io.insert-koin:koin-core:3.2.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.16")
}

sourceSets {
	val main by getting { }
	main.java.srcDirs("build/generated/source/proto/main/java")
	main.java.srcDirs("build/generated/source/proto/main/grpc")
	main.java.srcDirs("build/generated/source/proto/main/kotlin")
	main.java.srcDirs("build/generated/source/proto/main/grpckt")
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

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
	sourceSets.getByName("main").resources.srcDir("src/main/proto")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.7.0"
	}

	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
		}
	}

	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
			it.builtins {
				id("kotlin")
			}
		}
	}
}
