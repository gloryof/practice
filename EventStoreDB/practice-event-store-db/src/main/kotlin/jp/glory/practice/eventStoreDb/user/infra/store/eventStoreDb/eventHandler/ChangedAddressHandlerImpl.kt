package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddress
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddressHandler
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.ChangedAddressStoreDbData
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.ChangedAddressData
import org.springframework.stereotype.Component

@Component
class ChangedAddressHandlerImpl(
    private val client: EventStoreDbClient
) : ChangedAddressHandler {
    override fun handle(event: ChangedAddress) {
        toEventData(event)
            .also { client.append(ChangedAddressStoreDbData(it)) }
    }

    private fun toEventData(event: ChangedAddress): ChangedAddressData =
        ChangedAddressData(
            userId = event.userId.value,
            postalCode = event.address.postalCode.value,
            prefectureCode = event.address.prefecture.code,
            city = event.address.city.value,
            street = event.address.street.value
        )
}
