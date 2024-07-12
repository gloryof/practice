package jp.glory.practice.axon.user.domain.command

import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.UserId
import org.axonframework.modelling.command.TargetAggregateIdentifier

class ChangeAddressCommand(
    @TargetAggregateIdentifier val userId: UserId,
    val address: Address
)