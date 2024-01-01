
rootProject.name = "practice-arrow"
include("basic")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.9.20")
            version("arrow", "1.2.0")
            version("junit", "5.10.1")
            version("ksp", "1.8.21-1.0.11")

            library("arrow-core", "io.arrow-kt", "arrow-core").versionRef("arrow")
            library("arrow-fx", "io.arrow-kt", "arrow-fx-coroutines").versionRef("arrow")
            library("junit-bom", "org.junit" ,"junit-bom").versionRef("junit")
            library("junit-jupiter", "org.junit.jupiter" ,"junit-jupiter").withoutVersion()

            plugin("kotlin", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("ksp", "com.google.devtools.ksp").versionRef("ksp")
        }
    }
}