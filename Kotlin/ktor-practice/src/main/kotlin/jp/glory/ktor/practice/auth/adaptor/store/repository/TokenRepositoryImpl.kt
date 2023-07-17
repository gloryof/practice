package jp.glory.ktor.practice.auth.adaptor.store.repository

import jp.glory.ktor.practice.auth.adaptor.store.dao.TokenDao
import jp.glory.ktor.practice.auth.adaptor.store.dao.TokenTable
import jp.glory.ktor.practice.auth.domain.event.TokenAuthenticateEvent
import jp.glory.ktor.practice.auth.domain.model.AuthenticatedUserId
import jp.glory.ktor.practice.auth.domain.model.Token
import jp.glory.ktor.practice.auth.domain.model.TokenValue
import jp.glory.ktor.practice.auth.domain.model.UserId
import jp.glory.ktor.practice.auth.domain.repository.TokenRepository
import java.security.MessageDigest

class TokenRepositoryImpl(
    private val tokenDao: TokenDao
) : TokenRepository {
    private val digest = MessageDigest.getInstance("SHA-256")
    override fun save(token: Token) {
        tokenDao.save(
            TokenTable(
                tokenValue = token.tokenValue.value.let { hash(it) },
                id = token.userId.getValue()
            )
        )
    }

    override fun findToken(event: TokenAuthenticateEvent): Token? {
        val originalValue = event.tokenValue.value
        return tokenDao.find(hash(originalValue))
            ?.let {
                Token(
                    userId = AuthenticatedUserId(UserId(it.id)),
                    tokenValue = TokenValue(originalValue)
                )
            }
    }

    private fun hash(value :String): String =
        digest.digest(value.toByteArray())
            .joinToString(separator = "") {
                "%02x".format(it)
            }
}
