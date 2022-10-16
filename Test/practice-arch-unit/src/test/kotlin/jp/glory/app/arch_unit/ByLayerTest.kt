package jp.glory.app.arch_unit

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.library.Architectures
import com.tngtech.archunit.library.Architectures.LayeredArchitecture
import com.tngtech.archunit.library.metrics.ArchitectureMetrics
import com.tngtech.archunit.library.metrics.MetricsComponents
import org.junit.jupiter.api.Test


class ByLayerTest {
    companion object {
        const val basePackageName = "jp.glory.app.arch_unit"

        const val ktorLibrary = "Ktor Library"
        const val exposedLibrary = "Exposed Library"

        const val applicationBaseName = "Application Base"
        const val domainBaseLayerName = "Domain base"
        const val useCaseBaseLayerName = "UseCase base"
        const val webBaseLayerName = "Web base"

        const val moduleLayerName = "Module"
        const val domainLayerName = "Domain"
        const val useCaseLayerName = "UseCase"
        const val storeLayerName = "Store Adaptor"
        const val webLayerName = "Web Adaptor"

        val moduleNames = listOf("product")
    }

    @Test
    fun checkLayerDependency() {
        val basePackage = ClassFileImporter().importPackages(basePackageName)

        Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .apply {
                defineLayers(this)

                defineKtorLibrary(this)
                defineExposedLibrary(this)

                defineBaseDomainLayerRule(this)
                defineBaseUseCaseLayerRule(this)
                defineBaseWebLayerRule(this)

                defineDomainLayerRule(this)
                defineUseCaseLayerRule(this)
            }
            .check(basePackage)
    }
    @Test
    fun calculateLakosMetrics() {
        val targetPackage = ClassFileImporter().importPackages(basePackageName)
            .getPackage(basePackageName)
            .subpackagesInTree
        val components = MetricsComponents.fromPackages(targetPackage)

        val metrics = ArchitectureMetrics.lakosMetrics(components)

        println("CCD: " + metrics.getCumulativeComponentDependency())
        println("ACD: " + metrics.getAverageComponentDependency())
        println("RACD: " + metrics.getRelativeAverageComponentDependency())
        println("NCCD: " + metrics.getNormalizedCumulativeComponentDependency())

    }

    @Test
    fun calculateComponentDependencyMetricsMetrics() {
        val targetPackage = ClassFileImporter().importPackages(basePackageName)
            .getPackage(basePackageName)
            .subpackagesInTree
        val components = MetricsComponents.fromPackages(targetPackage)
        val metrics = ArchitectureMetrics.componentDependencyMetrics(components)

        targetPackage.forEach {
            val targetName = it.name
            println("======================== Start $targetName ======================== ")
            println("Ce: " + metrics.getEfferentCoupling(targetName))
            println("Ca: " + metrics.getAfferentCoupling(targetName))
            println("I: " + metrics.getInstability(targetName))
            println("A: " + metrics.getAbstractness(targetName))
            println("D: " + metrics.getNormalizedDistanceFromMainSequence(targetName))
            println("======================== E n d $targetName ======================== ")
        }
    }

    @Test
    fun calculateMetrics() {
        val targetPackage = ClassFileImporter().importPackages(basePackageName)
            .getPackage(basePackageName)
            .subpackagesInTree
        val components = MetricsComponents.fromPackages(targetPackage)
        val metrics = ArchitectureMetrics.visibilityMetrics(components)

        targetPackage.forEach {
            val targetName = it.name
            println("======================== Start $targetName ======================== ")
            println("RV : " + metrics.getRelativeVisibility(targetName))
            println("======================== E n d $targetName ======================== ")
        }

        println("ARV: " + metrics.getAverageRelativeVisibility())
        println("GRV: " + metrics.getGlobalRelativeVisibility())
    }

    private fun defineLayers(architecture: LayeredArchitecture) {
        architecture.optionalLayer(ktorLibrary)
            .definedBy("io.ktor..")
        architecture.optionalLayer(exposedLibrary)
            .definedBy("org.jetbrains.exposed..")

        architecture.layer(applicationBaseName)
            .definedBy(basePackageName)
        architecture.layer(domainBaseLayerName)
            .definedBy("$basePackageName.base.domain..")
        architecture.layer(useCaseBaseLayerName)
            .definedBy("$basePackageName.base.usecase..")
        architecture.layer(webBaseLayerName)
            .definedBy("$basePackageName.base.adaptor.web..")

        architecture.layer(moduleLayerName)
            .definedBy(*createPackageIdentifiers("module"))
        architecture.layer(domainLayerName)
            .definedBy(*createPackageIdentifiers("domain"))
        architecture.layer(useCaseLayerName)
            .definedBy(*createPackageIdentifiers("usecase"))
        architecture.layer(storeLayerName)
            .definedBy(*createPackageIdentifiers("adaptor.store"))
        architecture.layer(webLayerName)
            .definedBy(*createPackageIdentifiers("adaptor.web"))
    }

    private fun createPackageIdentifiers(
        layerName: String
    ): Array<String> =
        moduleNames
            .map { "$basePackageName.$it.$layerName.." }
            .toTypedArray()

    private fun defineKtorLibrary(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(ktorLibrary)
            .mayOnlyBeAccessedByLayers(
                webBaseLayerName
            )
    }

    private fun defineExposedLibrary(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(exposedLibrary)
            .mayOnlyBeAccessedByLayers(
                webLayerName,
                storeLayerName
            )
    }

    private fun defineBaseDomainLayerRule(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(domainBaseLayerName)
            .mayOnlyBeAccessedByLayers(
                domainLayerName,
                useCaseBaseLayerName,
                storeLayerName
            )
    }

    private fun defineBaseUseCaseLayerRule(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(useCaseBaseLayerName)
            .mayOnlyBeAccessedByLayers(
                useCaseLayerName,
                webBaseLayerName
            )
    }

    private fun defineBaseWebLayerRule(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(webBaseLayerName)
            .mayOnlyBeAccessedByLayers(
                applicationBaseName,
                webLayerName
            )
    }

    private fun defineDomainLayerRule(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(domainLayerName)
            .mayOnlyBeAccessedByLayers(
                storeLayerName,
                moduleLayerName,
                useCaseLayerName
            )
    }

    private fun defineUseCaseLayerRule(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(useCaseLayerName)
            .mayOnlyBeAccessedByLayers(
                applicationBaseName,
                moduleLayerName,
                webLayerName
            )
    }
}