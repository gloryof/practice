package jp.glory.practicegraphql.app.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.product.domain.model.*

interface ProductRepository {
    fun findAll(): Result<List<Product>, DomainUnknownError>
    fun findById(id: ProductID): Result<Product?, DomainUnknownError>
    fun findByProductCode(code: ProductCode): Result<List<Product>, DomainUnknownError>
    fun register(
        id: ProductID,
        event: RegisterProductEvent
    ): Result<ProductID, DomainUnknownError>

    fun save(event: UpdateProductEvent): Result<ProductID, DomainUnknownError>
}
