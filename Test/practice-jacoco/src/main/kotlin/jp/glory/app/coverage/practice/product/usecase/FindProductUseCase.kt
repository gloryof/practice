package jp.glory.app.coverage.practice.product.usecase

import com.github.michaelbull.result.*
import jp.glory.app.coverage.practice.base.usecase.UseCaseError
import jp.glory.app.coverage.practice.base.usecase.UseCaseNotFoundError
import jp.glory.app.coverage.practice.base.usecase.toUseCaseError
import jp.glory.app.coverage.practice.product.domain.model.Product
import jp.glory.app.coverage.practice.product.domain.model.ProductID
import jp.glory.app.coverage.practice.product.domain.repository.ProductRepository

class FindProductUseCase(
    private val repository: ProductRepository
) {
    fun findAll(): Result<ProductsSearchResult, UseCaseError> {
        return repository.findAll()
            .map { it.map { result -> ProductSearchResult(result) } }
            .map { ProductsSearchResult(it) }
            .mapError { toUseCaseError(it) }
    }

    fun findById(id: String): Result<ProductSearchResult, UseCaseError> {
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
