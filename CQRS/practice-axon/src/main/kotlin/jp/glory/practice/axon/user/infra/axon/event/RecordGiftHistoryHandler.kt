package jp.glory.practice.axon.user.infra.axon.event

import jp.glory.practice.axon.user.domain.event.RecordGiftHistory
import jp.glory.practice.axon.user.infra.store.GiftPointHistoryDao
import jp.glory.practice.axon.user.infra.store.GiftPointHistoryRecord
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class RecordGiftHistoryHandler(
    private val dao: GiftPointHistoryDao
) {
    @EventHandler
    fun on(event: RecordGiftHistory) {
        val record = GiftPointHistoryRecord(
            recordedAt = OffsetDateTime.now(),
            type = GiftPointHistoryRecord.HistoryType.fromType(event.type),
            userId = event.userId.value,
            amount = event.amount
        )
        dao.save(record)
    }
}