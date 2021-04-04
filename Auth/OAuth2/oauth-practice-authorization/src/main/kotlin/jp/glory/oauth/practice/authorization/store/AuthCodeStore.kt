package jp.glory.oauth.practice.authorization.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.authorization.base.Either
import jp.glory.oauth.practice.authorization.base.Left
import jp.glory.oauth.practice.authorization.base.Right
import jp.glory.oauth.practice.authorization.base.FatalError
import jp.glory.oauth.practice.authorization.spec.Scope
import jp.glory.oauth.practice.authorization.spec.auth_code.AuthCode
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

interface AuthCodeStore {
    fun register(
        code: AuthCode,
        state: String,
    ): Either<FatalError, Unit>
    fun findAuthCode(
        tokenValue: String,
    ): Either<FatalError, RegisteredAuthCode?>
    fun deleteAuthCode(
        code: AuthCode
    ): Either<FatalError, Unit>

    data class RegisteredAuthCode(
        val code: AuthCode,
        val state: String
    )
}

@Repository
class AuthCodeStoreImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : AuthCodeStore {
    private val keyPrefix = "auth:code"
    override fun register(
        code: AuthCode,
        state: String
    ): Either<FatalError, Unit> =
        kotlin.runCatching {
            val key = createKey(code.value)
            val expiresAt = LocalDateTime.now().plusMinutes(1)
            template.opsForValue().set(
                key,
                mapper.writeValueAsString(
                    AuthCodeInfo(
                        value = code.value,
                        scopes = code.scopes.map { it.name },
                        expiresAt = expiresAt,
                        state = state
                    )
                )
            )
            template.expireAt(
                key,
                expiresAt.toInstant(
                    OffsetDateTime.now().offset
                )
            )
        }
            .fold(
                onSuccess = { Right(Unit) },
                onFailure = { Left(FatalError("Fail saving value", it)) }
            )

    override fun findAuthCode(
        codeValue: String
    ): Either<FatalError, AuthCodeStore.RegisteredAuthCode?> =
        kotlin.runCatching {
            template.opsForValue()
                .get(createKey(codeValue))
                ?.let { mapper.readValue(it, AuthCodeInfo::class.java) }
                ?.let {
                    AuthCodeStore.RegisteredAuthCode(
                        code = AuthCode(
                            value = it.value,
                            scopes = it.scopes.mapNotNull { scope -> Scope.codeFrom(scope) },
                            expiresAt = it.expiresAt
                        ),
                        state = it.state
                    )
                }
        }
            .fold(
                onSuccess = { Right(it) },
                onFailure = { Left(FatalError("Fail getting value", it)) }
            )

    override fun deleteAuthCode(code: AuthCode): Either<FatalError, Unit> =
        kotlin.runCatching {
            template.delete(createKey(code.value))
        }
            .fold(
                onSuccess = { Right(Unit) },
                onFailure = { Left(FatalError("Fail delete value", it)) }
            )

    private fun createKey(
        codeValue: String
    ): String = "$keyPrefix:${codeValue}"

    data class AuthCodeInfo(
        val value: String,
        val scopes: List<String>,
        val expiresAt: LocalDateTime,
        val state: String,
    )
}