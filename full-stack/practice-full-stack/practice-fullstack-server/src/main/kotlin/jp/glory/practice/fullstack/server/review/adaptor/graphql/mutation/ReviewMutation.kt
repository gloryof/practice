package jp.glory.practice.fullstack.server.review.adaptor.graphql.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import jp.glory.practice.fullstack.server.base.adaptor.graphql.getGraphQLExecutor
import jp.glory.practice.fullstack.server.base.exception.NotFoundException
import jp.glory.practice.fullstack.server.review.adaptor.graphql.type.Review
import jp.glory.practice.fullstack.server.review.usecase.GetUserReviews
import jp.glory.practice.fullstack.server.review.usecase.RegisterReview
import jp.glory.practice.fullstack.server.review.usecase.UpdateReview

class ReviewMutation(
    private val registerReview: RegisterReview,
    private val updateReview: UpdateReview
) : GraphQLMutationResolver {
    fun registerReview(
        input: RegisterReviewInput,
        environment: DataFetchingEnvironment
    ): RegisterReviewPayload =
        environment.getGraphQLExecutor()
            .let {
                registerReview.register(
                    executeUser = it.toExecuteUser(),
                    input = RegisterReview.Input(
                        title = input.title,
                        rating = input.rating.toUInt()
                    )
                )
            }
            .let { RegisterReviewPayload(it.registeredId) }

    fun updateReview(
        input: UpdateReviewInput,
        environment: DataFetchingEnvironment
    ): UpdateReviewPayload =
        environment.getGraphQLExecutor()
            .let {
                updateReview.update(
                    executeUser = it.toExecuteUser(),
                    input = UpdateReview.Input(
                        id = input.id,
                        title = input.title,
                        rating = input.rating.toUInt()
                    )
                )
            }
            .let { UpdateReviewPayload(it.registeredId) }
}


class RegisterReviewInput(
    val title: String,
    val rating: Int
)

class RegisterReviewPayload(
    val id: String
)

class RegisterReviewPayloadResolver(
    private val getUserReviews: GetUserReviews
) : GraphQLResolver<RegisterReviewPayload> {

    fun getRegistered(
        payload: RegisterReviewPayload,
        environment: DataFetchingEnvironment
    ): Review =
        getUserReviews.getAll(environment.getGraphQLExecutor().toExecuteUser())
            .reviews
            .firstOrNull { it.id == payload.id }
            ?.let { Review(it) }
            ?: throw NotFoundException("Updated not found")
}

class UpdateReviewInput(
    val id: String,
    val title: String,
    val rating: Int
)

class UpdateReviewPayload(
    val id: String
)

class UpdateReviewPayloadResolver(
    private val getUserReviews: GetUserReviews
) : GraphQLResolver<UpdateReviewPayload> {

    fun getUpdated(
        payload: UpdateReviewPayload,
        environment: DataFetchingEnvironment
    ): Review =
        getUserReviews.getAll(environment.getGraphQLExecutor().toExecuteUser())
            .reviews
            .firstOrNull { it.id == payload.id }
            ?.let { Review(it) }
            ?: throw NotFoundException("Updated not found")
}
