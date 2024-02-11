package jp.glory.practice.fullstack.server.user.adaptor.grqphql.query

import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.schema.DataFetchingEnvironment
import jp.glory.practice.fullstack.server.base.adaptor.graphql.getGraphQLExecutor
import jp.glory.practice.fullstack.server.user.adaptor.grqphql.type.User
import jp.glory.practice.fullstack.server.user.usecase.GetUser

class UserQuery(
    private val getUser: GetUser
) : GraphQLQueryResolver {
    fun me(
        environment: DataFetchingEnvironment
    ): User =
        environment.getGraphQLExecutor()
            .let { getUser.getById(GetUser.Input(it.userId)) }
            .let { User(it) }
}