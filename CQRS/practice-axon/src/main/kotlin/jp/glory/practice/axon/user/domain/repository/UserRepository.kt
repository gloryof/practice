package jp.glory.practice.axon.user.domain.repository

import jp.glory.practice.axon.user.domain.model.User
import jp.glory.practice.axon.user.domain.model.UserId

interface UserRepository {
    fun findById(userId: UserId): User?
}