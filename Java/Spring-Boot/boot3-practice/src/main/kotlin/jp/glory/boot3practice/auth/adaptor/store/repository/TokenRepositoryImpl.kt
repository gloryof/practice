package jp.glory.boot3practice.auth.adaptor.store.repository

import jp.glory.boot3practice.auth.adaptor.store.dao.TokenDao
import jp.glory.boot3practice.auth.adaptor.store.dao.TokenTable
import jp.glory.boot3practice.auth.domain.event.TokenAuthenticateEvent
import jp.glory.boot3practice.auth.domain.model.AuthenticatedUserId
import jp.glory.boot3practice.auth.domain.model.Token
import jp.glory.boot3practice.auth.domain.model.TokenValue
import jp.glory.boot3practice.auth.domain.model.UserId
import jp.glory.boot3practice.auth.domain.repository.TokenRepository
import org.springframework.stereotype.Repository
import java.security.MessageDigest

@Repository
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