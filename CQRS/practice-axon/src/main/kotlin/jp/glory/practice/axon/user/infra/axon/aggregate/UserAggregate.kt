package jp.glory.practice.axon.user.infra.axon.aggregate

import jp.glory.practice.axon.user.domain.command.ChangeAddressCommand
import jp.glory.practice.axon.user.domain.command.ChangeNameCommand
import jp.glory.practice.axon.user.domain.command.ChargeGiftPointCommand
import jp.glory.practice.axon.user.domain.command.CreateUserCommand
import jp.glory.practice.axon.user.domain.command.UseGiftPointCommand
import jp.glory.practice.axon.user.domain.event.ChangedAddress
import jp.glory.practice.axon.user.domain.event.ChangedName
import jp.glory.practice.axon.user.domain.event.ChargedGiftPoint
import jp.glory.practice.axon.user.domain.event.CreatedUser
import jp.glory.practice.axon.user.domain.event.RecordGiftHistory
import jp.glory.practice.axon.user.domain.event.UsedGiftPoint
import jp.glory.practice.axon.user.domain.model.GiftPoint
import jp.glory.practice.axon.user.domain.model.User
import jp.glory.practice.axon.user.domain.model.UserId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class UserAggregate {

    @AggregateIdentifier
    private var userId: UserId? = null
    private var user: User? = null

    constructor()

    @CommandHandler
    constructor(command: CreateUserCommand) {
        userId = command.userId
        AggregateLifecycle.apply(
            CreatedUser.create(
                userId = command.userId,
                name = command.name,
                address = command.address
            )
        )
    }

    @EventSourcingHandler
    fun on(event: CreatedUser) {
        userId = event.userId
        user = User(
            userId = event.userId,
            name = event.name,
            address = event.address,
            giftPoint = GiftPoint(0U)
        )
    }

    @CommandHandler
    fun handle(command: ChangeNameCommand) {
        AggregateLifecycle.apply(
            ChangedName(
                userId = command.userId,
                name = command.name,
            )
        )
    }

    @EventSourcingHandler
    fun on(event: ChangedAddress) {
        user = requireNotNull(user)
            .changeAddress(event.address)
    }

    @CommandHandler
    fun handle(command: ChangeAddressCommand) {
        AggregateLifecycle.apply(
            ChangedAddress(
                userId = command.userId,
                address = command.address
            )
        )
    }

    @EventSourcingHandler
    fun on(event: ChargedGiftPoint) {
        user = requireNotNull(user)
            .chargeGiftPoint(event.chargeAmount)
    }

    @CommandHandler
    fun handle(command: ChargeGiftPointCommand) {
        AggregateLifecycle.apply(
            ChargedGiftPoint(
                userId = command.userId,
                chargeAmount = command.chargeAmount
            )
        )
        AggregateLifecycle.apply(
            RecordGiftHistory.charge(
                userId = command.userId,
                amount = command.chargeAmount
            )
        )
    }

    @EventSourcingHandler
    fun on(event: UsedGiftPoint) {
        user = requireNotNull(user)
            .useGiftPoint(event.useAmount)
    }

    @CommandHandler
    fun handle(command: UseGiftPointCommand) {
        AggregateLifecycle.apply(
            UsedGiftPoint(
                userId = command.userId,
                useAmount = command.useAmount
            )
        )
        AggregateLifecycle.apply(
            RecordGiftHistory.use(
                userId = command.userId,
                amount = command.useAmount
            )
        )
    }
}