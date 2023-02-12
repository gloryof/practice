package jp.glory.open_feature.practice.user.domain.repository

import jp.glory.open_feature.practice.user.domain.model.User
import jp.glory.open_feature.practice.user.domain.model.UserId

interface UserRepository {
    fun findAllUsers(): List<User>
    fun findById(id: UserId): User?
}