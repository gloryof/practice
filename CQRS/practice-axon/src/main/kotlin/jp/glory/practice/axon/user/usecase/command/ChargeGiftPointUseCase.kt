package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.command.ChargeGiftPointCommand
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class ChargeGiftPointUseCase(
    private val userRepository: UserRepository,
    private val commandGateway: CommandGateway
) {
    fun charge(input: Input) =
        userRepository.findById(UserId.fromString(input.userId))
            ?.let { it.executeChargeGiftPoint(input.amount) }
            ?.let { commandGateway.send<ChargeGiftPointCommand>(it) }
            ?: throw IllegalArgumentException("Use not found")

    class Input(
        val userId: String,
        val amount: UInt
    )

}