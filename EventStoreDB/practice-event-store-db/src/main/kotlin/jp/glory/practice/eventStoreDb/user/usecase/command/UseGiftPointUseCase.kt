package jp.glory.practice.eventStoreDb.user.usecase.command

import jp.glory.practice.eventStoreDb.user.domain.event.RecordedGiftHistoryHandler
import jp.glory.practice.eventStoreDb.user.domain.event.UsedGiftPointHandler
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UseGiftPointUseCase(
    private val repository: UserRepository,
    private val usedHandler: UsedGiftPointHandler,
    private val recordedHandler: RecordedGiftHistoryHandler
) {
    fun use(input: Input) {
        repository.findById(UserId.fromString(input.userId))
            ?.let { it.useGiftPoint(input.amount) }
            ?.also { usedHandler.handle(it) }
            ?.also { recordedHandler.handle(it.recordHistory()) }
            ?: throw IllegalArgumentException("User not found")
    }

    class Input(
        val userId: String,
        val amount: UInt
    )
}