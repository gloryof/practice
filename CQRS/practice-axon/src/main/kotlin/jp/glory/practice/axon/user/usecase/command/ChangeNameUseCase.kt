package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.command.ChangeNameCommand
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class ChangeNameUseCase(
    private val userRepository: UserRepository,
    private val commandGateway: CommandGateway
) {
    fun change(input: Input) =
        userRepository.findById(UserId.fromString(input.userId))
            ?.let { it.executeChangeName(UserName(input.name)) }
            ?.let { commandGateway.send<ChangeNameCommand>(it) }
            ?: throw IllegalArgumentException("Use not found")

    class Input(
        val userId: String,
        val name: String,
    )

}