package jp.glory.practice.eventStoreDb.user.domain.event

import jp.glory.practice.eventStoreDb.user.domain.model.Address
import jp.glory.practice.eventStoreDb.user.domain.model.UserId

class ChangedAddress(
    val userId: UserId,
    val address: Address
)

interface ChangedAddressHandler {
    fun handle(event: ChangedAddress)
}