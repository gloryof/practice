package jp.glory.practice.eventStoreDb.user.domain.repository

import jp.glory.practice.eventStoreDb.user.domain.model.User
import jp.glory.practice.eventStoreDb.user.domain.model.UserId

interface UserRepository {
    fun findById(userId: UserId): User?
}