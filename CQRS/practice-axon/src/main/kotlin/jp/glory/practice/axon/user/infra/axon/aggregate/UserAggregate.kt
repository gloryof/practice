package jp.glory.practice.axon.user.infra.axon.aggregate

import jp.glory.practice.axon.user.domain.event.CreatedUser
import jp.glory.practice.axon.user.infra.axon.command.CreateUserCommand
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class UserAggregate {

    @AggregateIdentifier
    private var id: String? = null

    constructor()

    @CommandHandler
    constructor(command: CreateUserCommand) {
        id = command.id
        AggregateLifecycle.apply(
            CreatedUser.create(
                userId = command.id,
                name = command.name,
                postalCode = command.postalCode,
                prefectureCode = command.prefectureCode,
                city = command.city,
                street = command.street
            )
        )
    }
}