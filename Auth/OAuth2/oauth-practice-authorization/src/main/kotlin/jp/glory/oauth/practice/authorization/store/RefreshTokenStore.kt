package jp.glory.oauth.practice.authorization.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.authorization.base.Either
import jp.glory.oauth.practice.authorization.base.Left
import jp.glory.oauth.practice.authorization.base.Right
import jp.glory.oauth.practice.authorization.base.ServerError
import jp.glory.oauth.practice.authorization.spec.refres_token.RefreshToken
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

interface RefreshTokenStore {
    fun save(token: RefreshToken): Either<ServerError, Unit>
    fun findByToken(tokenValue: String): Either<ServerError, RefreshToken?>
}

@Repository
class RefreshTokenStoreImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : RefreshTokenStore {
    private val keyPrefix = "auth:refresh"
    override fun save(token: RefreshToken): Either<ServerError, Unit> =
        kotlin.runCatching {
            template.opsForValue().set(
                createKey(token),
                token.accessTokenValue
            )
        }
            .fold(
                onSuccess = { Right(Unit) },
                onFailure = { Left(ServerError("Fail saving value", it)) }
            )

    override fun findByToken(tokenValue: String): Either<ServerError, RefreshToken?> =
        kotlin.runCatching {
            template.opsForValue()
                .get(createKey(tokenValue))
                ?.let {
                    RefreshToken(
                        value = tokenValue,
                        accessTokenValue = it
                    )
                }
        }
            .fold(
                onSuccess = { Right(it) },
                onFailure = { Left(ServerError("Fail saving value", it)) }
            )

    private fun createKey(
        token: RefreshToken
    ): String = createKey(token.value)

    private fun createKey(
        codeValue: String
    ): String = "$keyPrefix:${codeValue}"
}