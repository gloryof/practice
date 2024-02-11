package jp.glory.practice.fullstack.server.user.adaptor.grqphql

import graphql.kickstart.tools.GraphQLResolver
import jp.glory.practice.fullstack.server.user.adaptor.grqphql.mutation.UserMutation
import jp.glory.practice.fullstack.server.user.adaptor.grqphql.mutation.UserUpdatePayloadResolver
import jp.glory.practice.fullstack.server.user.adaptor.grqphql.query.UserQuery

class UserResolvers(
    private val userQuery: UserQuery,
    private val userMutation: UserMutation,
    private val updatePayloadResolver: UserUpdatePayloadResolver
) {
    fun getResolvers(): List<GraphQLResolver<*>> =
        listOf(
            userQuery,
            userMutation,
            updatePayloadResolver
        )
}