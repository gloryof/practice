package jp.glory.practice.fullstack.server.review.usecase

import jp.glory.practice.fullstack.server.base.usecase.ExecuteUser
import jp.glory.practice.fullstack.server.base.usecase.UseCase
import jp.glory.practice.fullstack.server.review.domain.RegisterReviewEvent
import jp.glory.practice.fullstack.server.review.domain.ReviewEventHandler
import jp.glory.practice.fullstack.server.user.domain.UserId

@UseCase
class RegisterReview(
    private val handler: ReviewEventHandler
) {
    fun register(
        executeUser: ExecuteUser,
        input: Input
    ): Output =
        RegisterReviewEvent.create(
           userId = UserId(executeUser.userId),
           title = input.title,
           rating = input.rating
        )
           .also { handler.register(it) }
           .let { Output(it.id.value) }

    class Input(
        val title: String,
        val rating: UInt
    )

    class Output(
        val registeredId: String,
    )
}