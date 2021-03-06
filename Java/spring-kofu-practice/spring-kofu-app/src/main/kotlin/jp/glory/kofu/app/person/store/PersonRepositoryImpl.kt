package jp.glory.kofu.app.person.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.kofu.app.person.domain.Person
import jp.glory.kofu.app.person.domain.PersonId
import jp.glory.kofu.app.person.domain.PersonRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PersonRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
): PersonRepository {
    override fun findById(id: PersonId): Person? =
        template.opsForValue()
            .get(generateKey(id))
            ?.let { mapper.readValue(it, PersonData::class.java) }
            ?.let {
                Person(
                    id = PersonId(it.id),
                    name = it.name,
                    age = it.age
                )
            }

    private fun generateKey(id: PersonId): String = "person:$id"
}