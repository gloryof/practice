package jp.glory.app.open_telemetry.practice.product.usecase

import com.github.michaelbull.result.*
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseError
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseNotFoundError
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseTelemetry
import jp.glory.app.open_telemetry.practice.base.usecase.toUseCaseError
import jp.glory.app.open_telemetry.practice.product.domain.model.Product
import jp.glory.app.open_telemetry.practice.product.domain.model.ProductID
import jp.glory.app.open_telemetry.practice.product.domain.repository.ProductRepository

class FindProductUseCase(
    private val repository: ProductRepository,
    private val useCaseTelemetry: UseCaseTelemetry
) {
    fun findAll(): Result<ProductsSearchResult, UseCaseError> {
        registerTelemetry("findAll")
        return repository.findAll()
            .map { it.map { result -> ProductSearchResult(result) } }
            .map { ProductsSearchResult(it) }
            .mapError { toUseCaseError(it) }
    }

    fun findById(id: String): Result<ProductSearchResult, UseCaseError> {
        registerTelemetry("findById")
        return repository.findById(ProductID(id))
            .mapBoth(
                success = {
                    if (it == null) {
                        createNotFound(id)
                    } else {
                        Ok(ProductSearchResult(it))
                    }
                },
                failure = { Err(toUseCaseError(it)) }
            )
    }

    private fun registerTelemetry(methodName: String) {
        useCaseTelemetry.registerUseCaseAttribute(
            useCaseName = "findProduct",
            methodName = methodName
        )
    }


    private fun createNotFound(id: String): Err<UseCaseNotFoundError> =
        Err(
            UseCaseNotFoundError(
                resourceName = UseCaseNotFoundError.ResourceName.Product,
                idValue = id
            )
        )

}

data class ProductsSearchResult(
    val results: List<ProductSearchResult>
)

data class ProductSearchResult(
    val id: String,
    val code: String,
    val name: String,
    val memberIDs: List<String>,
    val serviceIDs: List<String>
) {
    constructor(product: Product) : this(
        id = product.id.value,
        code = product.code.value,
        name = product.name.value,
        memberIDs = product.memberIDs.value.map { it.value },
        serviceIDs = product.serviceIDs.value.map { it.value }
    )
}
