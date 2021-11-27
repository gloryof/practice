package jp.glory.practicegraphql.app.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.product.domain.model.*
import jp.glory.practicegraphql.app.product.domain.repository.ProductRepository
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl : ProductRepository {
    private val products: MutableMap<String, Product> = mutableMapOf()

    init {
        repeat(3) { idVal ->
            val id = "product-id-$idVal"
            val childIds = (1..10)
                .filter { it % (idVal + 1) == 0 }
            val product = Product(
                id = ProductID(id),
                code = ProductCode("product-code-$idVal"),
                name = ProductName("product-name-$idVal"),
                memberIDs = childIds.map { MemberID("member-id-$it") },
                serviceIDs = childIds.map { ServiceID("service-id-$it") }
            )
            products[id] = product
        }
    }

    override fun findAll(): Result<List<Product>, DomainUnknownError> =
        Ok(products.values.toList())

    override fun findById(
        id: ProductID
    ): Result<Product?, DomainUnknownError> = Ok(products[id.value])
}
