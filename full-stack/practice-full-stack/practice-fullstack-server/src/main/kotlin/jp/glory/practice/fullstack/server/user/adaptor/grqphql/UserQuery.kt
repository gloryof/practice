package jp.glory.practice.fullstack.server.user.adaptor.grqphql

import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.schema.DataFetchingEnvironment
import jp.glory.practice.fullstack.server.base.adaptor.graphql.GraphQLExecutor
import jp.glory.practice.fullstack.server.base.ktor.plugin.GraphQLContextKeys
import jp.glory.practice.fullstack.server.user.usecase.GetUser

class UserQuery(
    private val getUser: GetUser
): GraphQLQueryResolver {
    fun me(
        environment: DataFetchingEnvironment
    ): User =
        environment.graphQlContext.get<GraphQLExecutor>(GraphQLContextKeys.Authorization.name)
            .let { getUser.getById(GetUser.Input(it.userId))  }
            .let {
                User(
                    id = it.id,
                    name = it.name,
                    birthday = it.birthday
                )
            }
}