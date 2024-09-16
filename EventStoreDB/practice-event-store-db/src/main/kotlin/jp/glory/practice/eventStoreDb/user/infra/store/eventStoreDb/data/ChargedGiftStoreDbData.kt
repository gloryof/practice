package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data

import java.time.OffsetDateTime

class ChargedGiftStoreDbData(
    body: ChargedGiftData
) : EventStoreDbEventData<ChargedGiftData>(body) {
    override fun getType(): EventDataType = EventDataType.ChargedGiftPoint
}

data class ChargedGiftData(
    val userId: String,
    val chargedAt: String,
    val chargeAmount: UInt,
)