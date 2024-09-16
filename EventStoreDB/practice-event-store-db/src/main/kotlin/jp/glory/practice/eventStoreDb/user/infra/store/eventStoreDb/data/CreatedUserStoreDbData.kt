package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data


class CreatedUserStoreDbData(
    body: CreatedUserData
) : EventStoreDbEventData<CreatedUserData>(body) {
    override fun getType(): EventDataType = EventDataType.CreatedUser
}


data class CreatedUserData(
    val userId: String,
    val userName: String,
    val postalCode: String,
    val prefectureCode: String,
    val city: String,
    val street: String
)