package jp.glory.oauth.practice.resource.context.user.infra

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.Left
import jp.glory.oauth.practice.resource.base.PasswordEncryptor
import jp.glory.oauth.practice.resource.base.Right
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.system.redis.RedisUserSchema
import jp.glory.oauth.practice.resource.context.user.domain.event.UserUpdateEvent
import jp.glory.oauth.practice.resource.context.user.domain.repository.UserUpdateEventRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class UserUpdateEventRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper,
    private val encryptor: PasswordEncryptor
) : UserUpdateEventRepository {
    override fun save(event: UserUpdateEvent): Either<DomainUnknownError, Unit> {
        return kotlin.runCatching {
            val userData = RedisUserSchema.UserData(
                name = event.name.value
            )

            template.opsForValue().set(
                RedisUserSchema.generateKey(event.userId.value),
                mapper.writeValueAsString(userData)
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