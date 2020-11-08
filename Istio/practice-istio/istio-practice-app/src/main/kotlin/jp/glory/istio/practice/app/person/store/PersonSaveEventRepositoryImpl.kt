package jp.glory.istio.practice.app.person.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.istio.practice.app.person.domain.PersonId
import jp.glory.istio.practice.app.person.domain.PersonRegisterEvent
import jp.glory.istio.practice.app.person.domain.PersonSaveEventRepository
import jp.glory.istio.practice.app.person.domain.PersonUpdateEvent
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PersonSaveEventRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : PersonSaveEventRepository {
    override fun register(event: PersonRegisterEvent): PersonId {
        val personId = PersonId(generateId())
        save(
            personId = personId,
            name = event.name,
            age = event.age
        )

        return personId
    }

    override fun update(event: PersonUpdateEvent): PersonId {
        save(
            personId = event.personId,
            name = event.name,
            age = event.age
        )

        return event.personId
    }

    private fun save(
        personId: PersonId,
        name: String,
        age: Int
    ) {
        template.opsForValue().set(
            generateKey(personId),
            mapper.writeValueAsString(
                PersonData(
                    id = personId.value,
                    name = name,
                    age = age
                )
            )
        )

    }

    private fun generateKey(id: PersonId): String = "person:$id"
    private fun generateId(): String = UUID.randomUUID().toString()
}