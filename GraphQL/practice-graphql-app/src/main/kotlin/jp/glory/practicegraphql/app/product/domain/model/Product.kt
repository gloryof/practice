package jp.glory.practicegraphql.app.product.domain.model

data class Product(
    val id: ProductID,
    val code: ProductCode,
    val name: ProductName,
    val members: List<MemberID>,
    val services: List<Service>
)

@JvmInline
value class ProductID(val value: String)

@JvmInline
value class ProductCode(val value: String)

@JvmInline
value class ProductName(val value: String)