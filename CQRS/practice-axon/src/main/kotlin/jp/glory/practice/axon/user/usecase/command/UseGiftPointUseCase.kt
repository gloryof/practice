package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.event.UsedGiftPointHandler
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UseGiftPointUseCase(
    private val userRepository: UserRepository,
    private val usedGiftPointHandler: UsedGiftPointHandler
) {
    fun use(input: Input) =
        userRepository.findById(UserId.fromString(input.userId))
            ?.let { it.useGiftPoint(input.amount) }
            ?.let { usedGiftPointHandler.handle(it) }
            ?: throw IllegalArgumentException("Use not found")

    class Input(
        val userId: String,
        val amount: UInt
    )

}