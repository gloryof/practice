package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data

import jp.glory.practice.eventStoreDb.user.domain.model.GiftPointHistory

class RecordedGiftHistoryStoreDbData(
    body: RecordedGiftHistoryData
) : EventStoreDbEventData<RecordedGiftHistoryData>(body) {
    override fun getType(): EventDataType = EventDataType.RecordedGiftHistory
}

data class RecordedGiftHistoryData(
    val recordedAt: String,
    val userId: String,
    val typeName: String,
    val amount: Int
) {
    fun toHistoryType(): HistoryType =
        HistoryType.values()
            .firstOrNull { it.name == typeName }
            ?: throw IllegalStateException("Type name is undefined")

    enum class HistoryType {
        Charge,
        Use;
        companion object {
            fun fromType(type: GiftPointHistory.Type): HistoryType =
                when(type) {
                    GiftPointHistory.Type.Charge -> Charge
                    GiftPointHistory.Type.Use -> Use
                }
        }
    }
}