package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.UsedGiftPoint
import jp.glory.practice.eventStoreDb.user.domain.event.UsedGiftPointHandler
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.UsedGiftData
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.UsedGiftStoreDbData
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class UsedGiftPointHandlerImpl(
    private val client: EventStoreDbClient
) : UsedGiftPointHandler {
    override fun handle(event: UsedGiftPoint) {
        toEventData(event)
            .also { client.append(UsedGiftStoreDbData(it)) }
    }

    private fun toEventData(event: UsedGiftPoint): UsedGiftData =
        UsedGiftData(
            usedAt = event.usedAt.toString(),
            userId = event.userId.value,
            useAmount = event.useAmount,
        )
}
