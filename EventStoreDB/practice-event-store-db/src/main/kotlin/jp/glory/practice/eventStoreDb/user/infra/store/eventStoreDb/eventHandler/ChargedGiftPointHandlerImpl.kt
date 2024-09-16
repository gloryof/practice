package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.ChargedGiftPoint
import jp.glory.practice.eventStoreDb.user.domain.event.ChargedGiftPointHandler
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.ChargedGiftData
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.ChargedGiftStoreDbData
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class ChargedGiftPointHandlerImpl(
    private val client: EventStoreDbClient
) : ChargedGiftPointHandler {
    override fun handle(event: ChargedGiftPoint) {
        toEventData(event)
            .also { client.append(ChargedGiftStoreDbData(it)) }
    }

    private fun toEventData(event: ChargedGiftPoint): ChargedGiftData =
        ChargedGiftData(
            chargedAt = event.chargedAt.toString(),
            userId = event.userId.value,
            chargeAmount = event.chargeAmount,
        )
}