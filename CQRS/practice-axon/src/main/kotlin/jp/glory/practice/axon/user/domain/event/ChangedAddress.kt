package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.UserId

class ChangedAddress(
    val userId: UserId,
    val address: Address
)