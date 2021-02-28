package jp.glory.oauth.practice.resource.context.auth.infra

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.Left
import jp.glory.oauth.practice.resource.base.PasswordEncryptor
import jp.glory.oauth.practice.resource.base.Right
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.context.auth.domain.model.EncryptedPassword
import jp.glory.oauth.practice.resource.context.auth.domain.model.IdentifierCredential
import jp.glory.oauth.practice.resource.context.auth.domain.repository.IdentifierCredentialRepository
import jp.glory.oauth.practice.resource.system.redis.RedisAuthSchema
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class IdentifierCredentialRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : IdentifierCredentialRepository {
    override fun findByLoginId(loginId: String): Either<DomainUnknownError, IdentifierCredential?> =
        kotlin.runCatching {
            template.opsForValue()
                .get(RedisAuthSchema.generateKey(loginId))
                ?.let { mapper.readValue(it, RedisAuthSchema.AuthData::class.java) }
                ?.let {
                    IdentifierCredential(
                        loginId = loginId,
                        password = EncryptedPassword(PasswordEncryptor.EncryptedValue(it.password))
                    )
                }
        }.fold(
            onSuccess = { Right(it) },
            onFailure = { Left(DomainUnknownError("Fail getting auth", it)) }
        )
}