package jp.glory.app.coverage.arch_unit

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.library.Architectures
import com.tngtech.archunit.library.Architectures.LayeredArchitecture
import org.junit.jupiter.api.Test

class ByLayerTest {
    companion object {
        const val basePackageName = "jp.glory.app.coverage.arch_unit"

        const val publicApiLayerName = "Public API"
        const val domainBaseLayerName = "Domain base"
        const val useCaseBaseLayerName = "UseCase base"

        const val domainLayerName = "Domain"

        const val storeName = "Store Adaptor"
    }

    @Test
    fun test() {
        val basePackage = ClassFileImporter().importPackages(basePackageName)

        Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .apply {
                defineLayers(this)
                defineBaseDomainLayerRule(this)
                defineDomainLayerRule(this)
            }
            .check(basePackage)
    }

    private fun defineLayers(architecture: LayeredArchitecture) {
        architecture.optionalLayer(publicApiLayerName)
            .definedBy(
                "java..",
                "kotlin..",
                "org.jetbrains..",
                "com.github.michaelbull..",
            )

        architecture.layer(domainBaseLayerName)
            .definedBy("$basePackageName.base.domain..")
        architecture.layer(useCaseBaseLayerName)
            .definedBy("$basePackageName.base.usecase..")

        architecture.layer(domainLayerName)
            .definedBy("$basePackageName..domain..")

        architecture.layer(storeName)
            .definedBy("$basePackageName..adaptor.store..")
    }

    private fun defineBaseDomainLayerRule(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(domainBaseLayerName)
            .mayOnlyBeAccessedByLayers(
                domainLayerName,
                useCaseBaseLayerName,
                storeName
            )
    }

    private fun defineDomainLayerRule(architecture: LayeredArchitecture) {
        architecture
            .whereLayer(domainLayerName)
            .mayOnlyAccessLayers(
                publicApiLayerName,
                domainBaseLayerName,
                storeName
            )
    }
}