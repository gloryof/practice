package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.RecordedGiftHistory
import jp.glory.practice.eventStoreDb.user.domain.event.RecordedGiftHistoryHandler
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.RecordedGiftHistoryData
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.RecordedGiftHistoryStoreDbData
import org.springframework.stereotype.Component

@Component
class RecordedGiftHistoryHandlerImpl(
    private val client: EventStoreDbClient
) : RecordedGiftHistoryHandler {
    override fun handle(event: RecordedGiftHistory) {
        toEventData(event)
            .also { client.append(RecordedGiftHistoryStoreDbData(it)) }
    }

    private fun toEventData(
        event: RecordedGiftHistory
    ): RecordedGiftHistoryData =
        RecordedGiftHistoryData(
            recordedAt = event.recordedAt.toString(),
            userId = event.userId.value,
            typeName = event.type.name,
            amount = event.amount.toInt()
        )
}

