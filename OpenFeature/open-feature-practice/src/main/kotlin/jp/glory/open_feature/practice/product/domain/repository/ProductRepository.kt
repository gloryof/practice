package jp.glory.open_feature.practice.product.domain.repository

import jp.glory.open_feature.practice.product.domain.model.*

interface ProductRepository {
    fun findAll(): List<Product>
    fun findById(id: ProductID): Product?
    fun findByProductCode(code: ProductCode): List<Product>
    fun register(
        id: ProductID,
        event: RegisterProductEvent
    ): ProductID

    fun save(event: UpdateProductEvent): ProductID
}