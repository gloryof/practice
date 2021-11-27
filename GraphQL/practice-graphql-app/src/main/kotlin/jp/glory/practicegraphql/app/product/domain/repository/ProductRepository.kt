package jp.glory.practicegraphql.app.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.product.domain.model.Product
import jp.glory.practicegraphql.app.product.domain.model.ProductID

interface ProductRepository {
    fun findAll(): Result<List<Product>, DomainUnknownError>
    fun findById(id: ProductID): Result<Product?, DomainUnknownError>
}
