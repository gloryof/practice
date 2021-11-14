package jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema

import jp.glory.practicegraphql.app.product.usecase.ProductSearchResult

data class Product(
    val id: String,
    val code: String,
    val name: String,
    val memberIds: List<String>,
    val serviceIds: List<String>
) {
    constructor(result: ProductSearchResult): this(
        id = result.id,
        code = result.code,
        name = result.name,
        memberIds = result.memberIDs,
        serviceIds = result.serviceIDs,
    )
}