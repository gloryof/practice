package jp.glory.open_feature.practice.product.use_case

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import jp.glory.open_feature.practice.base.use_case.UseCase
import jp.glory.open_feature.practice.base.use_case.UseCaseError
import jp.glory.open_feature.practice.base.use_case.UseCaseNotFoundError
import jp.glory.open_feature.practice.product.domain.model.Product
import jp.glory.open_feature.practice.product.domain.model.ProductID
import jp.glory.open_feature.practice.product.domain.repository.ProductRepository

@UseCase
class FindProductUseCase(
    private val repository: ProductRepository
) {
    fun findAll(): ProductsSearchResult =
        repository.findAll()
            .let { it.map { result -> ProductSearchResult(result) } }
            .let { ProductsSearchResult(it) }

    fun findById(id: String): Result<ProductSearchResult, UseCaseError> =
        repository.findById(ProductID(id))
            ?.let { Ok(ProductSearchResult(it)) }
            ?: createNotFound(id)

    private fun createNotFound(id: String): Err<UseCaseNotFoundError> =
        Err(
            UseCaseNotFoundError(
                resourceName = UseCaseNotFoundError.ResourceName.Product,
                idValue = id
            )
        )
}

data class ProductsSearchResult(
    val products: List<ProductSearchResult>
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