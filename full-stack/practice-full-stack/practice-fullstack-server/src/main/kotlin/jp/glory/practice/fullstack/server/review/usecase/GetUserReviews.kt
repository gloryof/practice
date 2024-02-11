package jp.glory.practice.fullstack.server.review.usecase

import jp.glory.practice.fullstack.server.base.usecase.ExecuteUser
import jp.glory.practice.fullstack.server.review.domain.Review
import jp.glory.practice.fullstack.server.review.domain.ReviewRepository
import jp.glory.practice.fullstack.server.review.domain.ReviewUserId
import java.time.OffsetDateTime

class GetUserReviews(
    private val repository: ReviewRepository
) {
    fun getAll(
        executeUser: ExecuteUser,
    ): Output =
        repository
            .findBYUserId(ReviewUserId(executeUser.userId))
            .map {
                toDetail(it)
            }
            .let { Output(it) }

    private fun toDetail(
        review: Review
    ): ReviewDetail =
        ReviewDetail(
            id = review.id.value,
            userId = review.userId.value,
            title = review.title.value,
            rating = review.rating.value,
            reviewAt = review.reviewAt.value,
            updatedAt = review.updatedAt?.value
        )

    class Input(
        val id: String,
        val title: String,
        val rating: UInt
    )

    class Output(
        val reviews: List<ReviewDetail>,
    )

    class ReviewDetail(
        val id: String,
        val userId: String,
        val title: String,
        val rating: UInt,
        val reviewAt: OffsetDateTime,
        val updatedAt: OffsetDateTime?
    )
}