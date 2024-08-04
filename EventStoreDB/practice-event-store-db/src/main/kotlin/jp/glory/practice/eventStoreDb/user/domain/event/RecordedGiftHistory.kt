package jp.glory.practice.eventStoreDb.user.domain.event

import jp.glory.practice.eventStoreDb.user.domain.model.GiftPointHistory
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import java.time.OffsetDateTime

class RecordedGiftHistory private constructor(
    val recordedAt: OffsetDateTime,
    val userId: UserId,
    val type: GiftPointHistory.Type,
    val amount: UInt
) {
    companion object {
        fun use(
            recordedAt: OffsetDateTime,
            userId: UserId,
            amount: UInt
        ): RecordedGiftHistory =
            RecordedGiftHistory(
                recordedAt = recordedAt,
                userId = userId,
                type = GiftPointHistory.Type.Use,
                amount = amount
            )

        fun charge(
            recordedAt: OffsetDateTime,
            userId: UserId,
            amount: UInt
        ): RecordedGiftHistory =
            RecordedGiftHistory(
                recordedAt = recordedAt,
                userId = userId,
                type = GiftPointHistory.Type.Charge,
                amount = amount
            )
    }
}

interface RecordedGiftHistoryHandler {
    fun handle(event: RecordedGiftHistory)
}