package jp.glory.practice.fullstack.server.review.adaptor.graphql.type

import jp.glory.practice.fullstack.server.review.usecase.GetUserReviews
import java.time.OffsetDateTime

class Reviews(
    val items: List<Review>
) {
    constructor(output: GetUserReviews.Output) : this(
        items = output.reviews.map { Review(it) }
    )
}

class Review(
    val id: String,
    val userId: String,
    val title: String,
    val rating: UInt,
    val reviewAt: OffsetDateTime,
    val updatedAt: OffsetDateTime?
) {
    constructor(detail: GetUserReviews.ReviewDetail) : this(
        id = detail.id,
        userId = detail.userId,
        title = detail.title,
        rating = detail.rating,
        reviewAt = detail.reviewAt,
        updatedAt = detail.updatedAt
    )
}