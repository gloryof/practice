package jp.glory.practice.measure.architecture.product.adaptor.web.graphql.schema

import jp.glory.practice.measure.architecture.product.usecase.MemberSearchResult
import jp.glory.practice.measure.architecture.product.usecase.ProductSearchResult
import jp.glory.practice.measure.architecture.product.usecase.ServiceSearchResult
import java.time.LocalDate

data class Products(
    val products: List<Product>
)

data class Product(
    val id: String,
    val code: String,
    val name: String,
    val memberIds: List<String>,
    val serviceIds: List<String>
) {
    constructor(result: ProductSearchResult) : this(
        id = result.id,
        code = result.code,
        name = result.name,
        memberIds = result.memberIDs,
        serviceIds = result.serviceIDs,
    )
}

data class Member(
    val id: String,
    val givenName: String,
    val familyName: String,
    val birthDay: LocalDate
) {
    constructor(result: MemberSearchResult) : this(
        id = result.id,
        givenName = result.givenName,
        familyName = result.familyName,
        birthDay = result.birthDay,
    )
}


data class Service(
    val id: String,
    val name: String,
    val kind: ServiceKind
) {
    constructor(result: ServiceSearchResult) : this(
        id = result.id,
        name = result.name,
        kind = when (result.kind) {
            ServiceSearchResult.ServiceKind.Finance -> ServiceKind.Finance
            ServiceSearchResult.ServiceKind.Entertainment -> ServiceKind.Entertainment
            ServiceSearchResult.ServiceKind.HealthCare -> ServiceKind.HealthCare
        }
    )
}

enum class ServiceKind {
    Finance,
    Entertainment,
    HealthCare
}

data class RegisterProductInput(
    val code: String,
    val name: String,
    val memberIDs: List<String>,
    val serviceIDs: List<String>
)

data class RegisterProductResult(
    val id: String
)

data class UpdateProductInput(
    val id: String,
    val code: String,
    val name: String,
    val memberIDs: List<String>,
    val serviceIDs: List<String>
)

data class UpdateProductResult(
    val id: String
)
