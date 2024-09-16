package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data



class ChangedAddressStoreDbData(
    body: ChangedAddressData
) : EventStoreDbEventData<ChangedAddressData>(body) {
    override fun getType(): EventDataType = EventDataType.ChangedAddress
}

data class ChangedAddressData(
    val userId: String,
    val postalCode: String,
    val prefectureCode: String,
    val city: String,
    val street: String,
)