package jp.glory.practice.axon.user.domain.command

import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName
import org.axonframework.modelling.command.TargetAggregateIdentifier

class ChangeNameCommand(
    @TargetAggregateIdentifier val userId: UserId,
    val name: UserName,
)