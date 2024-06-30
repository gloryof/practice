package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.infra.axon.command.CreateUserCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(
    private val commandGateway: CommandGateway
) {
    fun create(input: Input): Output =
        input.toCommand()
            .also { commandGateway.send<CreateUserCommand>(it) }
            .let { Output(it.id) }

    class Input(
        val name: String,
        val postalCode: String,
        val prefectureCode: String,
        val city: String,
        val street: String
    ) {
        fun toCommand(): CreateUserCommand =
            CreateUserCommand(
                id = UserId.generate().value,
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