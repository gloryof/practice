package jp.glory.oauth.practice.authorization.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.authorization.base.Either
import jp.glory.oauth.practice.authorization.base.Left
import jp.glory.oauth.practice.authorization.base.Right
import jp.glory.oauth.practice.authorization.base.FatalError
import jp.glory.oauth.practice.authorization.spec.access_token.AccessToken
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface AccessTokenStore {
    fun register(
        token: AccessToken,
        state: String? = null,
    ): Either<FatalError, Unit>
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
    ): Either<FatalError, Unit> =
    kotlin.runCatching {
        template.opsForValue().set(
            createKey(token),
            mapper.writeValueAsString(
                AccessTokenInfo(
                    value = token.value,
                    type = token.type,
                    scopes = token.scopes.map { it.name },
                    expiresAt = token.expiresAt,
                    state = state
                )
            )
        )
    }
        .fold(
            onSuccess = { Right(Unit) },
            onFailure = { Left(FatalError("Fail saving value", it)) }
        )

    private fun createKey(
        token: AccessToken
    ): String = "$keyPrefix:${token.value}"

    data class AccessTokenInfo(
        val value: String,
        val type: String,
        val scopes: List<String>,
        val expiresAt: LocalDateTime,
        val state: String?
    )
}

