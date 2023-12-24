package jp.glory.practice.grpc.user.useCase

import jp.glory.practice.grpc.base.usecase.UseCase
import jp.glory.practice.grpc.user.domain.event.RegisterUserEvent
import jp.glory.practice.grpc.user.domain.event.RegisterUserEventHandler
import jp.glory.practice.grpc.user.domain.model.UserId
import java.time.LocalDate

@UseCase
class RegisterUser(
    private val handler: RegisterUserEventHandler
) {
    fun register(input: Input): Output =
        RegisterUserEvent.create(
            userName = input.userName,
            birthday = input.birthday
        )
            .also { handler.register(it) }
            .let { Output(it.userId.value) }

    class Input(
        val userName: String,
        val birthday: LocalDate
    )

    class Output(
        val id: String
    )
}