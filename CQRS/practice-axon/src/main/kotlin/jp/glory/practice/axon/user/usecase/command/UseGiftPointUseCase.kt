package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.command.UseGiftPointCommand
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class UseGiftPointUseCase(
    private val userRepository: UserRepository,
    private val commandGateway: CommandGateway
) {
    fun use(input: Input) =
        userRepository.findById(UserId.fromString(input.userId))
            ?.let { it.executeUseGiftPoint(input.amount) }
            ?.let { commandGateway.send<UseGiftPointCommand>(it) }
            ?: throw IllegalArgumentException("Use not found")

    class Input(
        val userId: String,
        val amount: UInt
    )

}