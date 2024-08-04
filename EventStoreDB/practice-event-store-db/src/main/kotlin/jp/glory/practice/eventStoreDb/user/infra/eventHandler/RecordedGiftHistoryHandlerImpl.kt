package jp.glory.practice.eventStoreDb.user.infra.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.RecordedGiftHistory
import jp.glory.practice.eventStoreDb.user.domain.event.RecordedGiftHistoryHandler
import jp.glory.practice.eventStoreDb.user.infra.store.GiftPointHistoryDao
import jp.glory.practice.eventStoreDb.user.infra.store.GiftPointHistoryRecord
import org.springframework.stereotype.Component

@Component
class RecordedGiftHistoryHandlerImpl(
    private val dao: GiftPointHistoryDao
) : RecordedGiftHistoryHandler {
    override fun handle(event: RecordedGiftHistory) =
        toRecord(event)
            .let { dao.save(it) }

    private fun toRecord(
        event: RecordedGiftHistory
    ): GiftPointHistoryRecord =
        GiftPointHistoryRecord(
            recordedAt = event.recordedAt,
            userId = event.userId.value,
            type = GiftPointHistoryRecord.HistoryType.fromType(event.type),
            amount = event.amount
        )
}