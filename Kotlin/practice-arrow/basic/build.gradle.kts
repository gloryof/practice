
plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
