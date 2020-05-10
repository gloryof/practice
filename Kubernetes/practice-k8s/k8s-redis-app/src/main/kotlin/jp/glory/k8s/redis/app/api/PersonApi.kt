package jp.glory.k8s.redis.app.api

import jp.glory.k8s.redis.app.store.PersonData
import jp.glory.k8s.redis.app.store.PersonDataStore
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/person")
class PersonApi(
    private val dataStore: PersonDataStore
) {

    @GetMapping("{id}")
    fun get(@PathVariable id: String): ResponseEntity<PersonResponse> =
        dataStore.get(id)
            ?.let { PersonResponse(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping("{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: PersonRequest
    ): ResponseEntity<Any> {
        dataStore.save(
            id = id,
            name = request.name,
            age = request.age
        )

        return ResponseEntity.noContent().build()
    }

    @PostMapping
    fun register(@RequestBody request: PersonRequest): ResponseEntity<String> {
        val id = dataStore.save(
            name = request.name,
            age = request.age
        )

        return ResponseEntity.ok(id)
    }
}

data class PersonRequest(
    var name: String = "",
    var age: Int = 0
)

data class PersonResponse(
    val id: String,
    val name: String ,
    val age: Int
) {
    constructor(person: PersonData):
        this(
            id = person.id,
            name = person.name,
            age = person.age
        )
}