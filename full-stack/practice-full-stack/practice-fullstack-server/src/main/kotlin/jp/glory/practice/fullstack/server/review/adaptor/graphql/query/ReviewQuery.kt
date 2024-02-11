package jp.glory.practice.fullstack.server.review.adaptor.graphql.query

import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.schema.DataFetchingEnvironment
import jp.glory.practice.fullstack.server.base.adaptor.graphql.getGraphQLExecutor
import jp.glory.practice.fullstack.server.review.adaptor.graphql.type.Reviews
import jp.glory.practice.fullstack.server.review.usecase.GetUserReviews

class ReviewQuery(
    private val getUserReviews: GetUserReviews
) : GraphQLQueryResolver {
    fun reviews(
        environment: DataFetchingEnvironment
    ): Reviews =
        environment.getGraphQLExecutor()
            .let { getUserReviews.getAll(it.toExecuteUser()) }
            .let { Reviews(it) }
}