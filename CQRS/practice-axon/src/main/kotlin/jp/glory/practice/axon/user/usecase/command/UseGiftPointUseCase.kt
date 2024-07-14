package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.command.UseGiftPointCommand
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.query.FindUserQuery
import jp.glory.practice.axon.user.domain.query.FindUserResult
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.GenericQueryMessage
import org.axonframework.queryhandling.QueryBus
import org.springframework.stereotype.Service

@Service
class UseGiftPointUseCase(
    private val commandGateway: CommandGateway,
    private val queryBus: QueryBus
) {
    fun use(input: Input): Unit {
        val result = queryBus.query(toQuery(input.userId))
            .get()
            .let { it.payload }
            ?: throw IllegalArgumentException("User not found")

        result.toUseGiftPointCommand(input.amount)
            .let { commandGateway.send<UseGiftPointCommand>(it) }
    }

    private fun toQuery(userId: String): GenericQueryMessage<FindUserQuery, FindUserResult> =
        GenericQueryMessage(
            FindUserQuery(UserId.fromString(userId)),
            ResponseTypes.instanceOf(FindUserResult::class.java)
        )

    class Input(
        val userId: String,
        val amount: UInt
    )

}