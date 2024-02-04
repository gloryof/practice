package jp.glory.practice.fullstack.server.auth.adaptor.store

import jp.glory.practice.fullstack.server.auth.domain.AuthUserId
import jp.glory.practice.fullstack.server.auth.domain.AuthorizedToken
import jp.glory.practice.fullstack.server.auth.domain.AuthorizedUser
import jp.glory.practice.fullstack.server.auth.domain.AuthorizedUserRepository
import jp.glory.practice.fullstack.server.base.adaptor.store.TokenDao

class AuthorizedUserRepositoryImpl(
    private val dao: TokenDao
) : AuthorizedUserRepository {
    override fun findByToken(token: String): AuthorizedUser? =
        dao.findByToken(token)
            ?.let {
                AuthorizedUser(
                    userId = AuthUserId(it.userId),
                    authorizedToken = AuthorizedToken(it.token)
                )
            }
}