package jp.glory.practice.fullstack.server.review.domain

import jp.glory.practice.fullstack.server.user.domain.UserId
import java.util.UUID

class Review(
    val id: ReviewId,
    val userId: ReviewUserId,
    val title: Title,
    val rating: Rating
) {
    fun update(
        userId: ReviewUserId,
        title: Title,
        rating: Rating
    ): UpdateReviewEvent {
        if (userId != this.userId) {
            throw IllegalStateException("Not match review user")
        }
        return UpdateReviewEvent(
            id = id,
            userId = userId,
            title = title,
            rating = rating
        )
    }

}

@JvmInline
value class ReviewId(val value: String) {
    companion object {
        fun generate(): ReviewId =
            ReviewId(UUID.randomUUID().toString())
    }

    init {
        assert(value.isNotBlank())
    }
}


@JvmInline
value class ReviewUserId(val value: String) {
    init {
        assert(value.isNotBlank())
    }
}


@JvmInline
value class Title(val value: String) {
    init {
        assert(value.isNotBlank())
        assert(value.length < 100)
    }
}

@JvmInline
value class Rating(val value: UInt) {
    init {
        assert(value >= 1u)
        assert(value <= 5u)
    }
}

class RegisterReviewEvent private constructor(
    val id: ReviewId,
    val userId: ReviewUserId,
    val title: Title,
    val rating: Rating
) {
    companion object {
        fun create(
            userId: UserId,
            title: String,
            rating: UInt,
        ): RegisterReviewEvent =
            RegisterReviewEvent(
                id = ReviewId.generate(),
                userId = ReviewUserId(userId.value),
                title = Title(title),
                rating = Rating(rating)
            )
    }
}

class UpdateReviewEvent(
    val id: ReviewId,
    val userId: ReviewUserId,
    val title: Title,
    val rating: Rating
)


interface ReviewEventHandler {
    fun register(event: RegisterReviewEvent)
    fun update(event: UpdateReviewEvent)
}

interface ReviewRepository {
    fun findById(id: ReviewId): Review?
    fun findBYUserId(userId: ReviewUserId): List<Review>
}