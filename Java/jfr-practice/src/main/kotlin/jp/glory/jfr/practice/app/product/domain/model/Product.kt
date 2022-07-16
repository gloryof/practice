package jp.glory.jfr.practice.app.product.domain.model

import java.util.*

data class Product(
    val id: ProductID,
    val code: ProductCode,
    val name: ProductName,
    val memberIDs: MemberIds,
    val serviceIDs: ServiceIds
)

object ProductIdGenerator {
    fun generate(): ProductID =
        ProductID(UUID.randomUUID().toString())
}

data class RegisterProductEvent(
    val code: ProductCode,
    val name: ProductName,
    val memberIDs: MemberIds,
    val serviceIDs: ServiceIds
)

data class RegisterProductPreCheckResult(
    private val sameCodeProducts: List<Product>
) {
    fun hasSameCode(): Boolean =
        sameCodeProducts.isNotEmpty()
}

data class UpdateProductEvent(
    val id: ProductID,
    val code: ProductCode,
    val name: ProductName,
    val memberIDs: MemberIds,
    val serviceIDs: ServiceIds
)

data class UpdateProductPreCheckResult(
    private val targetProduct: Product,
    private val sameCodeProducts: List<Product>
) {
    fun hasSameCode(): Boolean =
        sameCodeProducts
            .filterNot { it.id == targetProduct.id }
            .isNotEmpty()
}


@JvmInline
value class ProductID(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

@JvmInline
value class ProductCode(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

@JvmInline
value class ProductName(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 50)
    }
}

@JvmInline
value class MemberIds(val value: List<MemberID>) {
    init {
        require(value.isNotEmpty())
    }
}

@JvmInline
value class ServiceIds(val value: List<ServiceID>) {
    init {
        require(value.isNotEmpty())
    }
}
