package jp.glory.practice.eventStoreDb.user.domain.model

import java.time.OffsetDateTime

class GiftPointHistory(
    val recordedAt: OffsetDateTime,
    val userId: UserId,
    val type: Type,
    val amount: UInt
) {
    enum class Type {
        Charge,
        Use
    }
}