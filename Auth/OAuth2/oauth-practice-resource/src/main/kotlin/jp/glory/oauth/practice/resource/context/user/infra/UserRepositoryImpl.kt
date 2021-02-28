package jp.glory.oauth.practice.resource.context.user.infra

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.Left
import jp.glory.oauth.practice.resource.base.Right
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.system.redis.RedisHobbySchema
import jp.glory.oauth.practice.resource.system.redis.RedisUserSchema
import jp.glory.oauth.practice.resource.context.user.domain.model.Hobby
import jp.glory.oauth.practice.resource.context.user.domain.model.User
import jp.glory.oauth.practice.resource.context.user.domain.model.UserId
import jp.glory.oauth.practice.resource.context.user.domain.model.UserName
import jp.glory.oauth.practice.resource.context.user.domain.repository.UserRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : UserRepository {
    override fun findById(id: UserId): Either<DomainUnknownError, User?> {
        val userData = template.opsForValue()
               .get(RedisUserSchema.generateKey(id.value))
               ?.let { mapper.readValue(it, RedisUserSchema.UserData::class.java) }

        return kotlin.runCatching {
            if (userData == null) return@runCatching null
            val hobbies = template.opsForValue()
                    .get(RedisHobbySchema.generateKey(id.value))
                    ?.let{ mapper.readValue(it, RedisHobbySchema.HobbyData::class.java) }

            User(
                id = id,
                name = UserName(userData.name),
                hobbies = hobbies
                    ?.let { it.hobbies.map { name -> Hobby(name) } }
                    ?: listOf()
            )
        }.fold(
            onSuccess = { Right(it) },
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