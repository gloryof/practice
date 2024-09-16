package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.listener

import com.eventstore.dbclient.ResolvedEvent
import com.eventstore.dbclient.Subscription
import com.eventstore.dbclient.SubscriptionListener
import com.fasterxml.jackson.module.kotlin.readValue
import jp.glory.practice.eventStoreDb.user.infra.store.dao.GiftPointHistoryDao
import jp.glory.practice.eventStoreDb.user.infra.store.dao.GiftPointHistoryRecord
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.RecordedGiftHistoryData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class RecordGiftHistoryDataListener(
    private val client: EventStoreDbClient,
    private val historyDao: GiftPointHistoryDao
) : SubscriptionListener() {
    override fun onEvent(subscription: Subscription, resolvedEvent: ResolvedEvent) {
        val event = resolvedEvent.originalEvent.eventData
        val parsed: RecordedGiftHistoryData = client.mapper.readValue(event)
        recordHistory(parsed)
    }

    private fun recordHistory(
        data: RecordedGiftHistoryData
    ) {
        historyDao.save(toHistoryRecord(data))
    }

    private fun toHistoryRecord(
        data: RecordedGiftHistoryData
    ): GiftPointHistoryRecord =
        GiftPointHistoryRecord(
            userId = data.userId,
            recordedAt = OffsetDateTime.parse(data.recordedAt),
            type = GiftPointHistoryRecord.HistoryType.Charge,
            amount = data.amount.toUInt()
        )
}