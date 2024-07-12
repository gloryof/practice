package jp.glory.practice.axon.user.domain.command

import jp.glory.practice.axon.user.domain.model.UserId
import org.axonframework.modelling.command.TargetAggregateIdentifier

class UseGiftPointCommand(
    @TargetAggregateIdentifier val userId: UserId,
    val useAmount: UInt
)