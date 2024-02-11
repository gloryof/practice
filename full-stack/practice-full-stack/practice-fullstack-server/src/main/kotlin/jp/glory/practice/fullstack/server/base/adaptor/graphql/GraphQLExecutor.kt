package jp.glory.practice.fullstack.server.base.adaptor.graphql

import jp.glory.practice.fullstack.server.base.usecase.ExecuteUser

class GraphQLExecutor(
    val userId: String,
) {
    fun toExecuteUser(): ExecuteUser =
        ExecuteUser(userId)
}