package jp.glory.practice.fullstack.server.auth.adaptor.store

import jp.glory.practice.fullstack.server.auth.domain.AuthUserId
import jp.glory.practice.fullstack.server.auth.domain.RegisteredPassword
import jp.glory.practice.fullstack.server.auth.domain.RegisteredUser
import jp.glory.practice.fullstack.server.auth.domain.RegisteredUserRepository
import jp.glory.practice.fullstack.server.base.adaptor.store.AuthDao

class RegisteredUserRepositoryImpl(
    private val dao: AuthDao
) : RegisteredUserRepository {
    override fun findById(userId: AuthUserId): RegisteredUser? =
        dao.findById(userId.value)
            ?.let {
                RegisteredUser(
                    userId = AuthUserId(it.userId),
                    password = RegisteredPassword(it.password)
                )
            }
}