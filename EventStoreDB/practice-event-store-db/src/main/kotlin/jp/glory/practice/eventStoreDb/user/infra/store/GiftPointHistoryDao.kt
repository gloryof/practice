package jp.glory.practice.eventStoreDb.user.infra.store

import jp.glory.practice.eventStoreDb.user.domain.model.GiftPointHistory
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.TreeMap

@Component
class GiftPointHistoryDao {
    private val histories = TreeMap<OffsetDateTime, GiftPointHistoryRecord>()

    fun save(record: GiftPointHistoryRecord) {
        histories[record.recordedAt] = record
    }

    fun findByUserId(id: String): List<GiftPointHistoryRecord> = histories
        .filter { it.value.userId == id }
        .values
        .toList()
}

data class GiftPointHistoryRecord(
    val recordedAt: OffsetDateTime,
    val userId: String,
    val type: HistoryType,
    val amount: UInt
) {
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