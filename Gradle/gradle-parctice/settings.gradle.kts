rootProject.name = "gradle-parctice"

dependencyResolutionManagement {
    versionCatalogs {
        create("springLib") {
            val bootVersion = "boot"
            val dependencyVersion = "dependency"

            version(bootVersion, "3.1.2")
            version(dependencyVersion, "1.1.2")
            plugin("boot", "org.springframework.boot").versionRef(bootVersion)
            plugin("dependency", "io.spring.dependency-management").versionRef(dependencyVersion)

        }
        create("kotlinLib") {
            val kotlinPluginVersion = "plugin"
            version(kotlinPluginVersion, "1.8.22")

            plugin("jvm", "org.jetbrains.kotlin.jvm").versionRef(kotlinPluginVersion)
            plugin("spring", "org.jetbrains.kotlin.plugin.spring").versionRef(kotlinPluginVersion)
        }

        create("libs") {
            library("either", "com.michael-bull.kotlin-result:kotlin-result:1.1.16")
        }
    }
}