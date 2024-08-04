package jp.glory.practice.eventStoreDb.user.usecase.query

import jp.glory.practice.eventStoreDb.user.domain.model.GiftPointHistory
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.repository.GiftPointHistoryRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class GetGiftHistoryUseCase(
    private val repository: GiftPointHistoryRepository
) {
    fun findById(input: Input): Output =
        repository.findByUserId(UserId.fromString(input.userId))
            .let { Output.create(it) }

    class Input(
        val userId: String
    )

    class Output private constructor(
        val details: List<Detail>
    ) {
        companion object {
            fun create(histories: List<GiftPointHistory>):Output =
                Output(
                    details = histories.map { Detail.create(it) },
                )
        }

        class Detail private constructor(
            val recordedAt: OffsetDateTime,
            val type: String,
            val amount: UInt
        ) {
            companion object {
                fun create(history: GiftPointHistory): Detail =
                    Detail(
                        recordedAt = history.recordedAt,
                        type = history.type.name,
                        amount = history.amount,
                    )
            }
        }
    }
}