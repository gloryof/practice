package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.event.CreatedUser
import jp.glory.practice.axon.user.domain.event.CreatedUserHandler
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(
    private val createdUserHandler: CreatedUserHandler
) {
    fun create(input: Input): Output =
        input.toEvent()
            .also { createdUserHandler.handle(it) }
            .let { Output(it.userId.value) }

    class Input(
        val name: String,
        val postalCode: String,
        val prefectureCode: String,
        val city: String,
        val street: String
    ) {
        fun toEvent(): CreatedUser =
            CreatedUser.create(
                name = name,
                postalCode = postalCode,
                prefectureCode = prefectureCode,
                city = city,
                street = street
            )
    }

    class Output(
        val userId: String
    )
}