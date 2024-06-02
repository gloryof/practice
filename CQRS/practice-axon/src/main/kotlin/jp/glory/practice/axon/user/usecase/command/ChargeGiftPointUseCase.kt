package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.event.ChargedGiftPointHandler
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ChargeGiftPointUseCase(
    private val userRepository: UserRepository,
    private val chargeGiftPointHandler: ChargedGiftPointHandler
) {
    fun charge(input: Input) =
        userRepository.findById(UserId.fromString(input.userId))
            ?.let { it.chargeGiftPoint(input.amount) }
            ?.let { chargeGiftPointHandler.handle(it) }
            ?: throw IllegalArgumentException("Use not found")

    class Input(
        val userId: String,
        val amount: UInt
    )

}