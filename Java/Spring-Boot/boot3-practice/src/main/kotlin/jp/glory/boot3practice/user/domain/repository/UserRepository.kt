package jp.glory.boot3practice.user.domain.repository

import jp.glory.boot3practice.user.domain.model.User

interface UserRepository {
    fun findAllUsers(): List<User>
}