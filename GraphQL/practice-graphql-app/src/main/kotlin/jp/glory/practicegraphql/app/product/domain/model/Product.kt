package jp.glory.practicegraphql.app.product.domain.model

data class Product(
    val id: ProductID,
    val code: ProductCode,
    val name: ProductName,
    val memberIDs: List<MemberID>,
    val serviceIDs: List<ServiceID>
)

@JvmInline
value class ProductID(val value: String)

@JvmInline
value class ProductCode(val value: String)

@JvmInline
value class ProductName(val value: String)