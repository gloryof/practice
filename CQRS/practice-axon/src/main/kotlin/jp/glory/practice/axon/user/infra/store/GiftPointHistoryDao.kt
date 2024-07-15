package jp.glory.practice.axon.user.infra.store

import jp.glory.practice.axon.user.domain.event.RecordGiftHistory
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
            fun fromType(type: RecordGiftHistory.Type): HistoryType =
                when(type) {
                    RecordGiftHistory.Type.Charge -> Charge
                    RecordGiftHistory.Type.Use -> Use
                }
        }
    }
}