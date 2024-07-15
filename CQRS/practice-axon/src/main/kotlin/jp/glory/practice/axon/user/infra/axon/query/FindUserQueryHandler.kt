package jp.glory.practice.axon.user.infra.axon.query

import jp.glory.practice.axon.user.domain.query.FindGiftPointHistoryQuery
import jp.glory.practice.axon.user.domain.query.FindGiftPointHistoryResult
import jp.glory.practice.axon.user.domain.query.FindGiftPointHistoryResultDetail
import jp.glory.practice.axon.user.infra.store.GiftPointHistoryDao
import jp.glory.practice.axon.user.infra.store.GiftPointHistoryRecord
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class FindUserQueryHandler(
    private val giftPointHistoryDao: GiftPointHistoryDao
) {
    @QueryHandler
    fun handle(query: FindGiftPointHistoryQuery): FindGiftPointHistoryResult =
        giftPointHistoryDao.findByUserId(query.userId.value)
            .map { toResult(it) }
            .let { FindGiftPointHistoryResult(it) }

    private fun toResult(record: GiftPointHistoryRecord): FindGiftPointHistoryResultDetail =
        FindGiftPointHistoryResultDetail(
            recordedAt = record.recordedAt,
            type = when (record.type) {
                GiftPointHistoryRecord.HistoryType.Charge -> FindGiftPointHistoryResultDetail.Type.Charge
                GiftPointHistoryRecord.HistoryType.Use -> FindGiftPointHistoryResultDetail.Type.Use
            },
            amount = record.amount,
        )
}