package jp.glory.practice.eventStoreDb.user.usecase.command

import jp.glory.practice.eventStoreDb.user.domain.event.ChargedGiftPointHandler
import jp.glory.practice.eventStoreDb.user.domain.event.RecordedGiftHistoryHandler
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ChargeGiftPointUseCase(
    private val repository: UserRepository,
    private val chargedHandler: ChargedGiftPointHandler,
    private val recordedHandler: RecordedGiftHistoryHandler
) {
    fun charge(input: Input): Unit {
        repository.findById(UserId.fromString(input.userId))
            ?.let { it.chargeGiftPoint(input.amount) }
            ?.also { chargedHandler.handle(it) }
            ?.also { recordedHandler.handle(it.recordHistory()) }
            ?: throw IllegalArgumentException("User not found")
    }

    class Input(
        val userId: String,
        val amount: UInt
    )

}