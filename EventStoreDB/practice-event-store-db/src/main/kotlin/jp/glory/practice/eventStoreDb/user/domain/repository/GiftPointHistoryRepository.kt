package jp.glory.practice.eventStoreDb.user.domain.repository

import jp.glory.practice.eventStoreDb.user.domain.model.GiftPointHistory
import jp.glory.practice.eventStoreDb.user.domain.model.UserId

interface GiftPointHistoryRepository {
    fun findByUserId(userId: UserId): List<GiftPointHistory>
}