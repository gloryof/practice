package jp.glory.practice.axon.user.usecase.query

import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.query.FindGiftPointHistoryQuery
import jp.glory.practice.axon.user.domain.query.FindGiftPointHistoryResult
import jp.glory.practice.axon.user.domain.query.FindGiftPointHistoryResultDetail
import jp.glory.practice.axon.user.usecase.query.GetUserUseCase.Output
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.GenericQueryMessage
import org.axonframework.queryhandling.QueryBus
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class GetGiftHistoryUseCase(
    private val queryBus: QueryBus
) {
    fun findById(input: Input): Output =
        queryBus.query(toQuery(input.userId))
            .get()
            .let { it.payload }
            ?.let { Output.create(it) }
            ?: throw IllegalArgumentException("User not found")

    private fun toQuery(
        userId: String
    ): GenericQueryMessage<FindGiftPointHistoryQuery, FindGiftPointHistoryResult> =
        GenericQueryMessage(
            FindGiftPointHistoryQuery(UserId.fromString(userId)),
            ResponseTypes.instanceOf(FindGiftPointHistoryResult::class.java)
        )


    class Input(
        val userId: String
    )

    class Output private constructor(
        val details: List<Detail>
    ) {
        companion object {
            fun create(result: FindGiftPointHistoryResult):Output =
                Output(
                    details = result.details.map { Detail.create(it) },
                )
        }

        class Detail private constructor(
            val recordedAt: OffsetDateTime,
            val type: String,
            val amount: UInt
        ) {
            companion object {
                fun create(detail: FindGiftPointHistoryResultDetail): Detail =
                    Detail(
                        recordedAt = detail.recordedAt,
                        type = detail.type.name,
                        amount = detail.amount,
                    )
            }
        }
    }
}