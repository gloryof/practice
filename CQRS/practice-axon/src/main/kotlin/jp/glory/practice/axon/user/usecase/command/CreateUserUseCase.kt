package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.command.CreateUserCommand
import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.City
import jp.glory.practice.axon.user.domain.model.PostalCode
import jp.glory.practice.axon.user.domain.model.Prefecture
import jp.glory.practice.axon.user.domain.model.Street
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(
    private val commandGateway: CommandGateway
) {
    fun create(input: Input): Output =
        input.toCommand()
            .also { commandGateway.send<CreateUserCommand>(it) }
            .let { Output(it.userId.value) }

    class Input(
        val name: String,
        val postalCode: String,
        val prefectureCode: String,
        val city: String,
        val street: String
    ) {
        fun toCommand(): CreateUserCommand =
            CreateUserCommand(
                userId = UserId.generate(),
                name = UserName(name),
                address = Address(
                    postalCode = PostalCode(postalCode),
                    prefecture = Prefecture.fromCode(prefectureCode),
                    city = City(city),
                    street = Street(street)
                ),
            )
    }

    class Output(
        val userId: String
    )
}