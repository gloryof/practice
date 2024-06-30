package jp.glory.practice.axon.user.infra.axon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

class CreateUserCommand(
    @TargetAggregateIdentifier val id: String,
    val name: String,
    val postalCode: String,
    val prefectureCode: String,
    val city: String,
    val street: String,
)