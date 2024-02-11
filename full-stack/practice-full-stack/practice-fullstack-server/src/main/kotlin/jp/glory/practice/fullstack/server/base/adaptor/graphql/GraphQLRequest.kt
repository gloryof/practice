package jp.glory.practice.fullstack.server.base.adaptor.graphql

data class GraphQLRequest(
    val query: String = "",
    val operationName: String? = "",
    val variables: Map<String, Any>? = mapOf()
)