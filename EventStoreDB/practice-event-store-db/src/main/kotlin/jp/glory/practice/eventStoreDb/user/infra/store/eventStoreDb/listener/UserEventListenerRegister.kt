package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.listener

import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.EventDataType
import org.springframework.stereotype.Component

@Component
class UserEventListenerRegister(
    client: EventStoreDbClient,
    createdUserDataListener: CreatedUserDataListener,
    changedNameDataListener: ChangedNameDataListener,
    changedAddressDataListener: ChangedAddressDataListener,
    usedGiftPointDataListener: UsedGiftPointDataListener,
    chargedGiftPointDataListener: ChargedGiftPointDataListener,
    recordGiftHistoryDataListener: RecordGiftHistoryDataListener
) {
    init {
        client.subscribe(EventDataType.CreatedUser, createdUserDataListener)
        client.subscribe(EventDataType.ChangedName, changedNameDataListener)
        client.subscribe(EventDataType.ChangedAddress, changedAddressDataListener)
        client.subscribe(EventDataType.UsedGiftPoint, usedGiftPointDataListener)
        client.subscribe(EventDataType.ChargedGiftPoint, chargedGiftPointDataListener)
        client.subscribe(EventDataType.RecordedGiftHistory, recordGiftHistoryDataListener)
    }
}