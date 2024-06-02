package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.UserId

interface ChangedAddressHandler {
    fun handle(event: ChangedAddress)
}

class ChangedAddress(
    val userId: UserId,
    val address: Address
)