package jp.glory.k8s.hpa.app.person.api

import jp.glory.k8s.hpa.app.base.api.InvalidRequestException
import jp.glory.k8s.hpa.app.person.usecase.PersonResult
import jp.glory.k8s.hpa.app.person.usecase.PersonSaveUseCase
import jp.glory.k8s.hpa.app.person.usecase.PersonSearchUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/person")
class PersonApi(
    private val searchUseCase: PersonSearchUseCase,
    private val saveUseCase: PersonSaveUseCase
) {
    @GetMapping("{id}")
    fun get(@PathVariable id: String): ResponseEntity<PersonResponse> =
        searchUseCase.findById(id)
            ?.let { PersonResponse(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PutMapping("{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: PersonRequest
    ): ResponseEntity<Any> {
        saveUseCase.update(
            personId = id,
            name = request.name,
            age = request.age
        ).throwIfLeft {
            InvalidRequestException("Request is invalid.", it)
        }

        return ResponseEntity.noContent().build()
    }

    @PostMapping
    fun register(@RequestBody request: PersonRequest): ResponseEntity<String> {

        val id = saveUseCase.register(
                name = request.name,
                age = request.age
            ).throwIfLeft {
                InvalidRequestException("Request is invalid.", it)
            }

        return ResponseEntity.ok(id)
    }

    @GetMapping("error")
    fun error() {
        throw RuntimeException("error")
    }
}

data class PersonRequest(
    var name: String? = null,
    var age: Int? = null
)

data class PersonResponse(
    val id: String,
    val name: String ,
    val age: Int
) {
    constructor(person: PersonResult):
            this(
                id = person.id,
                name = person.name,
                age = person.age
            )
}

data class PersonsResponse(
    val result: List<PersonResponse>
)