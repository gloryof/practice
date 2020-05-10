package jp.glory.k8s.redis.app.store

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class PersonDataStore(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) {
    fun save(
        id: String? = null,
        name: String,
        age: Int
    ): String {
        val personId = id ?: generateId()
        val personData = PersonData(
            id = personId,
            name = name,
            age = age
        )

        template.opsForValue().set(
            generateKey(personId),
            mapper.writeValueAsString(personData)
        )

        return personId
    }

    fun get(id: String): PersonData? =
        template.opsForValue().get(generateKey(id))
            ?.let {
                mapper.readValue(it, PersonData::class.java)
            }

    private fun generateKey(id: String): String = "person:$id"

    private fun generateId(): String = UUID.randomUUID().toString()
}

data class PersonData(
    val id: String,
    val name: String,
    val age: Int
)