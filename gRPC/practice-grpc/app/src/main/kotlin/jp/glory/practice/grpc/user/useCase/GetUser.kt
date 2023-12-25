package jp.glory.practice.grpc.user.useCase

import jp.glory.practice.grpc.base.usecase.UseCase
import jp.glory.practice.grpc.user.domain.model.User
import jp.glory.practice.grpc.user.domain.model.UserId
import jp.glory.practice.grpc.user.domain.repository.UserRepository
import java.time.LocalDate

@UseCase
class GetUser(
    private val repository: UserRepository
) {
    fun findById(id: String): Output =
        repository.findById(UserId(id))
            ?.let { toOutput(it) }
            ?: throw IllegalArgumentException("User not found")

    private fun toOutput(
        user: User
    ): Output =
        Output(
            userId = user.id.value,
            userName = user.name.value,
            birthday = user.birthday.value
        )

    class Output(
        val userId: String,
        val userName: String,
        val birthday: LocalDate
    )
}