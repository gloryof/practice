package jp.glory.practice.eventStoreDb.user.infra.repository

import jp.glory.practice.eventStoreDb.user.domain.model.GiftPointHistory
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.repository.GiftPointHistoryRepository
import jp.glory.practice.eventStoreDb.user.infra.store.GiftPointHistoryDao
import jp.glory.practice.eventStoreDb.user.infra.store.GiftPointHistoryRecord
import org.springframework.stereotype.Repository

@Repository
class GiftPointHistoryRepositoryImpl(
    private val dao: GiftPointHistoryDao
) : GiftPointHistoryRepository {
    override fun findByUserId(userId: UserId): List<GiftPointHistory> =
        dao.findByUserId(userId.value)
            .map { toDomain(it) }

    private fun toDomain(record: GiftPointHistoryRecord): GiftPointHistory =
        GiftPointHistory(
            recordedAt = record.recordedAt,
            userId = UserId.fromString(record.userId),
            type = when (record.type) {
                GiftPointHistoryRecord.HistoryType.Charge -> GiftPointHistory.Type.Charge
                GiftPointHistoryRecord.HistoryType.Use -> GiftPointHistory.Type.Use
            },
            amount = record.amount
        )
}