package jp.glory.practicegraphql.app.product.domain.usecase

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import jp.glory.practicegraphql.app.base.usecase.UseCaseError
import jp.glory.practicegraphql.app.base.usecase.toUseCaseError
import jp.glory.practicegraphql.app.product.domain.model.*
import jp.glory.practicegraphql.app.product.domain.repository.ProductRepository

class FindProductUseCase(
    private val repository: ProductRepository
) {
    fun findById(id: String): Result<ProductSearchResult?, UseCaseError> =
        repository.findById(ProductID(id))
            .map { toResult(it) }
            .mapError { toUseCaseError(it) }

    private fun toResult(product: Product?): ProductSearchResult? =
        product?.let{ ProductSearchResult(it) }
}

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
        memberIDs = product.memberIDs.map { it.value },
        serviceIDs = product.serviceIDs.map { it.value }
    )
}