package jp.glory.oauth.practice.resource.context.user.infra

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.Left
import jp.glory.oauth.practice.resource.base.PasswordEncryptor
import jp.glory.oauth.practice.resource.base.Right
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.system.redis.RedisAuthSchema
import jp.glory.oauth.practice.resource.system.redis.RedisUserSchema
import jp.glory.oauth.practice.resource.context.user.domain.event.UserRegisterEvent
import jp.glory.oauth.practice.resource.context.user.domain.repository.UserRegisterEventRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRegisterEventRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper,
    private val encryptor: PasswordEncryptor
) : UserRegisterEventRepository {
    override fun save(event: UserRegisterEvent): Either<DomainUnknownError, Unit> {
        val userData = RedisUserSchema.UserData(
            name = event.name.value
        )
        val authData = RedisAuthSchema.AuthData(
            password = encryptor.encrypt(event.plainPassword).value
        )

        return kotlin.runCatching {
            template.opsForValue().set(
                RedisUserSchema.generateKey(event.userId.value),
                mapper.writeValueAsString(userData)
            )

            template.opsForValue().set(
                RedisAuthSchema.generateKey(event.userId.value),
                mapper.writeValueAsString(authData)
            )
        }.fold(
            onSuccess = { Right(Unit) },
            onFailure = {
                Left(
                    DomainUnknownError(
                        message = "Fail by unknown error",
                        cause = it
                    )
                )
            }
        )
    }
}