package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data

class UsedGiftStoreDbData(
    body: UsedGiftData
) : EventStoreDbEventData<UsedGiftData>(body) {
    override fun getType(): EventDataType = EventDataType.UsedGiftPoint
}

data class UsedGiftData(
    val userId: String,
    val usedAt: String,
    val useAmount: UInt,
)