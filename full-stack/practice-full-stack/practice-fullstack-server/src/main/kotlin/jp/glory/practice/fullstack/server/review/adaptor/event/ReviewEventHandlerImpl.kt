package jp.glory.practice.fullstack.server.review.adaptor.event

import jp.glory.practice.fullstack.server.base.adaptor.store.ReviewDao
import jp.glory.practice.fullstack.server.base.adaptor.store.ReviewRecord
import jp.glory.practice.fullstack.server.base.exception.NotFoundException
import jp.glory.practice.fullstack.server.review.domain.RegisterReviewEvent
import jp.glory.practice.fullstack.server.review.domain.ReviewEventHandler
import jp.glory.practice.fullstack.server.review.domain.UpdateReviewEvent

class ReviewEventHandlerImpl(
    private val dao: ReviewDao
) : ReviewEventHandler {
    override fun register(event: RegisterReviewEvent) {
        ReviewRecord(
            id = event.id.value,
            userId = event.userId.value,
            title = event.title.value,
            rating = event.rating.value,
            reviewAt = event.reviewAt.value,
            updatedAt = null
        )
            .let { dao.save(it) }
    }

    override fun update(event: UpdateReviewEvent) {
        dao.findById(event.id.value)
            ?.let {
                ReviewRecord(
                    id = event.id.value,
                    userId = event.userId.value,
                    title = event.title.value,
                    rating = event.rating.value,
                    reviewAt = it.reviewAt,
                    updatedAt = event.updatedAt.value
                )
            }
            ?.let { dao.save(it) }
            ?: NotFoundException("Not found review")
    }
}