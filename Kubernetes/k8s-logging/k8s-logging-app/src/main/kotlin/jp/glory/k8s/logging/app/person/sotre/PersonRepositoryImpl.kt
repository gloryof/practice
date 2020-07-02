package jp.glory.k8s.logging.app.person.sotre

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.k8s.logging.app.person.domain.Person
import jp.glory.k8s.logging.app.person.domain.PersonId
import jp.glory.k8s.logging.app.person.domain.PersonRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

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