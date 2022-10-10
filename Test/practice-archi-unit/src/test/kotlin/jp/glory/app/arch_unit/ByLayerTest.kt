package jp.glory.app.arch_unit

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.library.Architectures
import com.tngtech.archunit.library.Architectures.LayeredArchitecture
import org.junit.jupiter.api.Test

class ByLayerTest {
    companion object {
        const val basePackageName = "jp.glory.app.arch_unit"

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
    fun test() {
        val basePackage = ClassFileImporter().importPackages(basePackageName)

        Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .apply {
                defineLayers(this)

                defineBaseDomainLayerRule(this)
                defineBaseUseCaseLayerRule(this)
                defineBaseWebLayerRule(this)

                defineDomainLayerRule(this)
                defineUseCaseLayerRule(this)
            }
            .check(basePackage)
    }

    private fun defineLayers(architecture: LayeredArchitecture) {
        architecture.optionalLayer(applicationBaseName)
            .definedBy(basePackageName)
        architecture.layer(domainBaseLayerName)
            .definedBy("$basePackageName.base.domain..")
        architecture.layer(useCaseBaseLayerName)
            .definedBy("$basePackageName.base.usecase..")
        architecture.layer(webBaseLayerName)
            .definedBy("$basePackageName.base.adaptor.web..")

        architecture.optionalLayer(moduleLayerName)
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