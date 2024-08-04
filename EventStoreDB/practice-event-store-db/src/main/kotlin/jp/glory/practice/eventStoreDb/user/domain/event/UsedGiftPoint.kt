package jp.glory.practice.eventStoreDb.user.domain.event

import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import java.time.OffsetDateTime

class UsedGiftPoint private constructor(
    val usedAt: OffsetDateTime,
    val userId: UserId,
    val useAmount: UInt
) {
    companion object {
        fun create(
            userId: UserId,
            useAmount: UInt
        ): UsedGiftPoint =
            UsedGiftPoint(
                usedAt = OffsetDateTime.now(),
                userId = userId,
                useAmount = useAmount
            )
    }

    fun recordHistory(): RecordedGiftHistory =
        RecordedGiftHistory.use(
            recordedAt = usedAt,
            userId = userId,
            amount = useAmount
        )
}

interface UsedGiftPointHandler {
    fun handle(event: UsedGiftPoint)
}