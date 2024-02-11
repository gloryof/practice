package jp.glory.practice.fullstack.server.review.adaptor.graphql

import graphql.kickstart.tools.GraphQLResolver
import jp.glory.practice.fullstack.server.review.adaptor.graphql.mutation.RegisterReviewPayloadResolver
import jp.glory.practice.fullstack.server.review.adaptor.graphql.mutation.ReviewMutation
import jp.glory.practice.fullstack.server.review.adaptor.graphql.mutation.UpdateReviewPayloadResolver
import jp.glory.practice.fullstack.server.review.adaptor.graphql.query.ReviewQuery

class ReviewResolvers(
    val reviewMutation: ReviewMutation,
    val reviewQuery: ReviewQuery,
    val registerReviewPayloadResolver: RegisterReviewPayloadResolver,
    val updateReviewPayloadResolver: UpdateReviewPayloadResolver
) {

    fun getResolvers(): List<GraphQLResolver<*>> =
        listOf(
            reviewMutation,
            reviewQuery,
            registerReviewPayloadResolver,
            updateReviewPayloadResolver
        )
}