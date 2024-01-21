package jp.glory.practice.fullstack.server.review.adaptor.event

import jp.glory.practice.fullstack.server.base.adaptor.store.ReviewDao
import jp.glory.practice.fullstack.server.base.adaptor.store.ReviewRecord
import jp.glory.practice.fullstack.server.review.domain.RegisterReviewEvent
import jp.glory.practice.fullstack.server.review.domain.ReviewEventHandler
import jp.glory.practice.fullstack.server.review.domain.UpdateReviewEvent
import org.springframework.stereotype.Component

@Component
class ReviewEventHandlerImpl(
    private val dao: ReviewDao
) : ReviewEventHandler {
    override fun register(event: RegisterReviewEvent) {
        ReviewRecord(
            id = event.id.value,
            userId = event.userId.value,
            title = event.title.value,
            rating = event.rating.value
        )
            .let { dao.save(it) }
    }

    override fun update(event: UpdateReviewEvent) {
        ReviewRecord(
            id = event.id.value,
            userId = event.userId.value,
            title = event.title.value,
            rating = event.rating.value
        )
            .let { dao.save(it) }
    }
}