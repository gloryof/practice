package jp.glory.oauth.practice.resource.context.user.infra

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.Left
import jp.glory.oauth.practice.resource.base.Right
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.system.redis.RedisHobbySchema
import jp.glory.oauth.practice.resource.context.user.domain.event.ChangeHobbiesEvent
import jp.glory.oauth.practice.resource.context.user.domain.repository.ChangeHobbiesEventRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ChangeHobbiesEventRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper,
) : ChangeHobbiesEventRepository {
    override fun save(event: ChangeHobbiesEvent): Either<DomainUnknownError, Unit> =
        kotlin.runCatching {
            RedisHobbySchema.HobbyData(
                hobbies = event.hobbies.map { it.name }
            ).let {
                template.opsForValue().set(
                    RedisHobbySchema.generateKey(event.userId.value),
                    mapper.writeValueAsString(it)
                )
            }
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