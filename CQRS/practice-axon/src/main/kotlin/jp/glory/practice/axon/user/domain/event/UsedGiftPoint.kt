package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.UserId

class UsedGiftPoint(
    val userId: UserId,
    val useAmount: UInt
)