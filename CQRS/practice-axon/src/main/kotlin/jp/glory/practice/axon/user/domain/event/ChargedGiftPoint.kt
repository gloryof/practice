package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.UserId

class ChargedGiftPoint(
    val userId: UserId,
    val chargeAmount: UInt
)