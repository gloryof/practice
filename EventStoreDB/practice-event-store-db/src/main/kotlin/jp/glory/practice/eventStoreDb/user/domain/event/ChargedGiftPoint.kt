package jp.glory.practice.eventStoreDb.user.domain.event

import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import java.time.OffsetDateTime

class ChargedGiftPoint private constructor(
    val chargedAt: OffsetDateTime,
    val userId: UserId,
    val chargeAmount: UInt
) {
    companion object {
        fun create(
            userId: UserId,
            chargeAmount: UInt
        ): ChargedGiftPoint =
            ChargedGiftPoint(
                chargedAt = OffsetDateTime.now(),
                userId = userId,
                chargeAmount = chargeAmount
            )
    }

    fun recordHistory(): RecordedGiftHistory =
        RecordedGiftHistory.charge(
            recordedAt = chargedAt,
            userId = userId,
            amount = chargeAmount
        )
}

interface ChargedGiftPointHandler {
    fun handle(event: ChargedGiftPoint)
}