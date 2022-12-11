package jp.glory.boot3practice.user.domain.repository

import jp.glory.boot3practice.user.domain.model.User
import jp.glory.boot3practice.user.domain.model.UserId

interface UserRepository {
    fun findAllUsers(): List<User>
    fun findById(id: UserId): User?
}