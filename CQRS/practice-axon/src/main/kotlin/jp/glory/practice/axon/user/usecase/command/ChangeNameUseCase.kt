package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.command.ChangeNameCommand
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName
import jp.glory.practice.axon.user.domain.query.FindUserQuery
import jp.glory.practice.axon.user.domain.query.FindUserResult
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.GenericQueryMessage
import org.axonframework.queryhandling.QueryBus
import org.springframework.stereotype.Service

@Service
class ChangeNameUseCase(
    private val commandGateway: CommandGateway,
    private val queryBus: QueryBus
) {
    fun change(input: Input): Unit {
        val result = queryBus.query(toQuery(input.userId))
            .get()
            .let { it.payload }
            ?: throw IllegalArgumentException("User not found")

        result.toChangeNameCommand(UserName(input.name))
            .let { commandGateway.send<ChangeNameCommand>(it) }
    }

    private fun toQuery(userId: String): GenericQueryMessage<FindUserQuery, FindUserResult> =
        GenericQueryMessage(
            FindUserQuery(UserId.fromString(userId)),
            ResponseTypes.instanceOf(FindUserResult::class.java)
        )

    class Input(
        val userId: String,
        val name: String,
    )

}