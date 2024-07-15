package jp.glory.practice.axon.user.domain.query

import jp.glory.practice.axon.user.domain.model.UserId
import java.time.OffsetDateTime

class FindGiftPointHistoryQuery(
    val userId: UserId,
)

class FindGiftPointHistoryResult(
    val details: List<FindGiftPointHistoryResultDetail>
)

class FindGiftPointHistoryResultDetail(
    val recordedAt: OffsetDateTime,
    val type: Type,
    val amount: UInt
) {
    enum class Type {
        Charge,
        Use
    }
}