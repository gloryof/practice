package jp.glory.practice.fullstack.server.review.adaptor.store

import jp.glory.practice.fullstack.server.base.adaptor.store.ReviewDao
import jp.glory.practice.fullstack.server.base.adaptor.store.ReviewRecord
import jp.glory.practice.fullstack.server.review.domain.Rating
import jp.glory.practice.fullstack.server.review.domain.Review
import jp.glory.practice.fullstack.server.review.domain.ReviewId
import jp.glory.practice.fullstack.server.review.domain.ReviewRepository
import jp.glory.practice.fullstack.server.review.domain.ReviewUserId
import jp.glory.practice.fullstack.server.review.domain.Title
import org.springframework.stereotype.Repository

@Repository
class ReviewRepositoryImpl(
    private val dao: ReviewDao
) : ReviewRepository {
    override fun findById(
        id: ReviewId
    ): Review? =
        dao.findById(id.value)
            ?.let { toDomain(it) }

    override fun findBYUserId(
        userId: ReviewUserId
    ): List<Review> =
        dao.findByUserId(userId.value)
            .map { toDomain(it) }

    private fun toDomain(
        record: ReviewRecord
    ): Review =
        Review(
            id = ReviewId(record.id),
            userId = ReviewUserId(record.userId),
            title = Title(record.title),
            rating = Rating(record.rating)
        )
}