package jp.glory.oauth.practice.authorization.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.authorization.base.*
import jp.glory.oauth.practice.authorization.spec.Scope
import jp.glory.oauth.practice.authorization.spec.auth_code.AuthCode
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.OffsetDateTime

interface AuthCodeStore {
    fun register(
        code: AuthCode,
        state: String,
    ): Either<ServerError, Unit>
    fun findAuthCode(
        tokenValue: String,
    ): Either<ServerError, RegisteredAuthCode?>
    fun deleteAuthCode(
        code: AuthCode
    ): Either<ServerError, Unit>

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
    ): Either<ServerError, Unit> =
        kotlin.runCatching {
            val key = createKey(code.value)
            val expiresAt = LocalDateTime.now().plusSeconds(AuthCode.authCodeExpireSecond)
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
                    DateTime.getDefaultOffset()
                )
            )
        }
            .fold(
                onSuccess = { Right(Unit) },
                onFailure = { Left(ServerError("Fail saving value", it)) }
            )

    override fun findAuthCode(
        codeValue: String
    ): Either<ServerError, AuthCodeStore.RegisteredAuthCode?> =
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
                onFailure = { Left(ServerError("Fail getting value", it)) }
            )

    override fun deleteAuthCode(code: AuthCode): Either<ServerError, Unit> =
        kotlin.runCatching {
            template.delete(createKey(code.value))
        }
            .fold(
                onSuccess = { Right(Unit) },
                onFailure = { Left(ServerError("Fail delete value", it)) }
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