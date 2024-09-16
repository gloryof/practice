package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data

class ChangedNameStoreDbData(
    body: ChangedNameData
) : EventStoreDbEventData<ChangedNameData>(body) {
    override fun getType(): EventDataType = EventDataType.ChangedName
}

data class ChangedNameData(
    val userId: String,
    val userName: String,
)