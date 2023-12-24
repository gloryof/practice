package jp.glory.practice.grpc.user.useCase

import jp.glory.practice.grpc.base.usecase.UseCase
import jp.glory.practice.grpc.user.domain.event.UpdateUserEvent
import jp.glory.practice.grpc.user.domain.event.UpdateUserEventHandler
import jp.glory.practice.grpc.user.domain.model.Birthday
import jp.glory.practice.grpc.user.domain.model.User
import jp.glory.practice.grpc.user.domain.model.UserId
import jp.glory.practice.grpc.user.domain.model.UserName
import jp.glory.practice.grpc.user.domain.repository.UserRepository
import java.time.LocalDate

@UseCase
class UpdateUser(
    private val repository: UserRepository,
    private val handler: UpdateUserEventHandler
) {
    fun update(input: Input) {
        repository.findById(UserId(input.userId))
            ?.let { createEvent(it, input) }
            ?.let { handler.update(it) }
            ?: throw IllegalArgumentException("User not found")
    }

    private fun createEvent(
        user: User,
        input: Input
    ): UpdateUserEvent =
        user.update(
            newName = UserName(input.userName),
            newBirthday = Birthday(input.birthday)
        )

    class Input(
        val userId: String,
        val userName: String,
        val birthday: LocalDate
    )

}