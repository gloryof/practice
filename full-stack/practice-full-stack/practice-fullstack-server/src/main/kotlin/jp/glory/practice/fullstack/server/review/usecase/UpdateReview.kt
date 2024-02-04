package jp.glory.practice.fullstack.server.review.usecase

import jp.glory.practice.fullstack.server.base.usecase.ExecuteUser
import jp.glory.practice.fullstack.server.review.domain.Rating
import jp.glory.practice.fullstack.server.review.domain.ReviewEventHandler
import jp.glory.practice.fullstack.server.review.domain.ReviewId
import jp.glory.practice.fullstack.server.review.domain.ReviewRepository
import jp.glory.practice.fullstack.server.review.domain.ReviewUserId
import jp.glory.practice.fullstack.server.review.domain.Title

class UpdateReview(
    private val handler: ReviewEventHandler,
    private val repository: ReviewRepository
) {
    fun update(
        executeUser: ExecuteUser,
        input: Input
    ): Output =
        repository
            .findById(ReviewId(input.id))
            ?.let {
                it.update(
                    userId = ReviewUserId(executeUser.userId),
                    title = Title(input.title),
                    rating = Rating(input.rating)
                )
            }
            ?.also { handler.update(it) }
            ?.let { Output(it.id.value) }
            ?: throw IllegalStateException("Update is failed")


    class Input(
        val id: String,
        val title: String,
        val rating: UInt
    )

    class Output(
        val registeredId: String,
    )
}