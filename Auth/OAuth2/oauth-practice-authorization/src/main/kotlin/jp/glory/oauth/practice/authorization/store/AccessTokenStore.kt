package jp.glory.oauth.practice.authorization.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.authorization.base.*
import jp.glory.oauth.practice.authorization.spec.Scope
import jp.glory.oauth.practice.authorization.spec.access_token.AccessToken
import jp.glory.oauth.practice.authorization.spec.auth_code.AuthCode
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.OffsetDateTime

interface AccessTokenStore {
    fun register(
        token: AccessToken,
        state: String? = null,
    ): Either<ServerError, Unit>
    fun findByTokenValue(
        tokenValue: String
    ): Either<ServerError, AccessToken?>
    fun revokeToken(
        token: AccessToken
    ): Either<ServerError, Unit>
}

@Repository
class AccessTokenStoreImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : AccessTokenStore {
    private val keyPrefix = "auth:token"
    override fun register(
        token: AccessToken,
        state: String?,
    ): Either<ServerError, Unit> =
        kotlin.runCatching {
            val key = createKey(token)
            val expiresAt = LocalDateTime.now().plusSeconds(token.expiresIn)

            template.opsForValue().set(
                key,
                mapper.writeValueAsString(
                    AccessTokenInfo(
                        value = token.value,
                        type = token.type,
                        scopes = token.scopes.map { it.name },
                        expiresIn = token.expiresIn,
                        state = state
                    )
                )
            )
            template.expireAt(
                key,
                expiresAt.toInstant(
                    DateTime.getDefaultOffset()
                )
            )
        }
            .fold(
                onSuccess = { Right(Unit) },
                onFailure = { Left(ServerError("Fail saving value", it)) }
            )

    override fun findByTokenValue(tokenValue: String): Either<ServerError, AccessToken?> =
        kotlin.runCatching {
            template.opsForValue()
                .get(createKey(tokenValue))
                ?.let { mapper.readValue(it, AccessTokenInfo::class.java) }
                ?.let {
                    AccessToken(
                        value = it.value,
                        type = it.type,
                        scopes = it.scopes.mapNotNull { scope -> Scope.codeFrom(scope) },
                        expiresIn = it.expiresIn
                    )
                }
        }
            .fold(
                onSuccess = { Right(it) },
                onFailure = { Left(ServerError("Fail find by token", it)) }
            )


    override fun revokeToken(token: AccessToken): Either<ServerError, Unit> =
        kotlin.runCatching {
            template.delete(createKey(token))
        }
            .fold(
                onSuccess = { Right(Unit) },
                onFailure = { Left(ServerError("Fail delete token", it)) }
            )

    private fun createKey(
        token: AccessToken
    ): String = createKey(token.value)

    private fun createKey(
        value: String
    ): String = "$keyPrefix:${value}"

    data class AccessTokenInfo(
        val value: String,
        val type: String,
        val scopes: List<String>,
        val expiresIn: Long,
        val state: String?
    )
}

