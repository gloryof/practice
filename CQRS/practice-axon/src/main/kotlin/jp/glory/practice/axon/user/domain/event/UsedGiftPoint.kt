package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.UserId


interface UsedGiftPointHandler {
    fun handle(event: UsedGiftPoint)
}

class UsedGiftPoint(
    val userId: UserId,
    val useAmount: UInt
)