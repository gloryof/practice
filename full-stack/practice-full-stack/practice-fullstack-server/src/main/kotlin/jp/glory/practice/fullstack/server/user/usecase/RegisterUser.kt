package jp.glory.practice.fullstack.server.user.usecase

import jp.glory.practice.fullstack.server.base.usecase.UseCase
import jp.glory.practice.fullstack.server.user.domain.RegisterUserEvent
import jp.glory.practice.fullstack.server.user.domain.UserEventHandler
import java.time.LocalDate

@UseCase
class RegisterUser(
    private val userEventHandler: UserEventHandler
) {
    fun register(input: Input): Output =
        RegisterUserEvent.create(
            name = input.name,
            birthday = input.birthday,
            password = input.password
        )
            .also { userEventHandler.register(it) }
            .let { Output(it.id.value) }

    class Input(
        val name: String,
        val birthday: LocalDate,
        val password: String
    )

    class Output(
        val registeredId: String,
    )
}