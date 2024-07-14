package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.command.ChangeAddressCommand
import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.City
import jp.glory.practice.axon.user.domain.model.PostalCode
import jp.glory.practice.axon.user.domain.model.Prefecture
import jp.glory.practice.axon.user.domain.model.Street
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.query.FindUserQuery
import jp.glory.practice.axon.user.domain.query.FindUserResult
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.GenericQueryMessage
import org.axonframework.queryhandling.QueryBus
import org.springframework.stereotype.Service

@Service
class ChangeAddressUseCase(
    private val commandGateway: CommandGateway,
    private val queryBus: QueryBus
) {
    fun change(input: Input): Unit {
        val result = queryBus.query(toQuery(input.userId))
            .get()
            .let { it.payload }
            ?: throw IllegalArgumentException("User not found")

        result.toChangeAddressCommand(input.toAddress())
            .let { commandGateway.send<ChangeAddressCommand>(it) }
    }

    private fun toQuery(userId: String): GenericQueryMessage<FindUserQuery, FindUserResult> =
        GenericQueryMessage(
            FindUserQuery(UserId.fromString(userId)),
            ResponseTypes.instanceOf(FindUserResult::class.java)
        )

    class Input(
        val userId: String,
        val postalCode: String,
        val prefectureCode: String,
        val city: String,
        val street: String
    ) {
        fun toAddress(): Address =
            Address(
                postalCode = PostalCode(postalCode),
                prefecture = Prefecture.fromCode(prefectureCode),
                city = City(city),
                street = Street(street)
            )
    }
}