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
        repeat(10) { idVal ->
            val id = "product-id-$idVal"
            val product = Product(
                id = ProductID(id),
                code = ProductCode("product-code-$idVal"),
                name = ProductName("product-name-$idVal"),
                memberIDs = List(10) { it -> MemberID("member-id-$it") },
                serviceIDs = List(10) { it -> ServiceID("service-id-$it") }
            )
            products[id] = product
        }
    }

    override fun findById(
        id: ProductID
    ): Result<Product?, DomainUnknownError> = Ok(products[id.value])
}
