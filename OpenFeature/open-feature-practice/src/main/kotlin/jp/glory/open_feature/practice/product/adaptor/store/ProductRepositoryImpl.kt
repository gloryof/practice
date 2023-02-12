package jp.glory.open_feature.practice.product.adaptor.store

import jp.glory.open_feature.practice.product.domain.model.*
import jp.glory.open_feature.practice.product.domain.repository.ProductRepository
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
                memberIDs = MemberIds(childIds.map { MemberID("member-id-$it") }),
                serviceIDs = ServiceIds(childIds.map { ServiceID("service-id-$it") })
            )
            products[id] = product
        }
    }

    override fun findAll(): List<Product> =
        products.values.toList()

    override fun findById(
        id: ProductID
    ): Product? = products[id.value]

    override fun findByProductCode(code: ProductCode): List<Product> =
        products
            .filterValues { it.code == code }
            .values
            .toList()

    override fun register(id: ProductID, event: RegisterProductEvent): ProductID {
        products[id.value] = Product(
            id = id,
            code = event.code,
            name = event.name,
            memberIDs = event.memberIDs,
            serviceIDs = event.serviceIDs
        )

        return id
    }

    override fun save(event: UpdateProductEvent): ProductID {
        products[event.id.value] = Product(
            id = event.id,
            code = event.code,
            name = event.name,
            memberIDs = event.memberIDs,
            serviceIDs = event.serviceIDs
        )

        return event.id
    }
}