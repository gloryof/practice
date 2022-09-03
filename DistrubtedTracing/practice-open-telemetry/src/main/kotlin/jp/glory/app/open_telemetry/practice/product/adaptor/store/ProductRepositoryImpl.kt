package jp.glory.app.open_telemetry.practice.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.app.open_telemetry.practice.base.domain.DomainUnknownError
import jp.glory.app.open_telemetry.practice.product.domain.model.*
import jp.glory.app.open_telemetry.practice.product.domain.repository.ProductRepository

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
                memberIDs = MemberIds(childIds.map { MemberID("member-id-$it") }),
                serviceIDs = ServiceIds(childIds.map { ServiceID("service-id-$it") })
            )
            products[id] = product
        }
    }

    override fun findAll(): Result<List<Product>, DomainUnknownError> =
        Ok(products.values.toList())

    override fun findById(
        id: ProductID
    ): Result<Product?, DomainUnknownError> = Ok(products[id.value])

    override fun findByProductCode(code: ProductCode): Result<List<Product>, DomainUnknownError> =
        products
            .filterValues { it.code == code }
            .values
            .toList()
            .let { Ok(it) }

    override fun register(id: ProductID, event: RegisterProductEvent): Result<ProductID, DomainUnknownError> {
        products[id.value] = Product(
            id = id,
            code = event.code,
            name = event.name,
            memberIDs = event.memberIDs,
            serviceIDs = event.serviceIDs
        )

        return Ok(id)
    }

    override fun save(event: UpdateProductEvent): Result<ProductID, DomainUnknownError> {
        products[event.id.value] = Product(
            id = event.id,
            code = event.code,
            name = event.name,
            memberIDs = event.memberIDs,
            serviceIDs = event.serviceIDs
        )

        return Ok(event.id)
    }
}
