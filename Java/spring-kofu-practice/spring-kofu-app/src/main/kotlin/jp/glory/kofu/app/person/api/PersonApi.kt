package jp.glory.kofu.app.person.api

import jp.glory.kofu.app.base.api.InvalidRequestException
import jp.glory.kofu.app.person.usecase.PersonResult
import jp.glory.kofu.app.person.usecase.PersonSaveUseCase
import jp.glory.kofu.app.person.usecase.PersonSearchUseCase
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.lang.IllegalStateException

class PersonApi(
    private val searchUseCase: PersonSearchUseCase,
    private val saveUseCase: PersonSaveUseCase
) {
    fun get(request: ServerRequest): Mono<out ServerResponse> =
        request.pathVariable("id")
            ?.let { searchUseCase.findById(it)  }
            ?.let { PersonResponse(it) }
            ?.let {
                    ServerResponse.ok().bodyValue(it)
            }
            ?: ServerResponse.notFound().build()

    fun update(request: ServerRequest): Mono<out ServerResponse> {
        request.bodyToMono(PersonRequest::class.java)
            .map {
                saveUseCase.update(
                    personId = request.pathVariable("id"),
                    name = it.name,
                    age = it.age
                ).throwIfLeft { err ->
                    InvalidRequestException("Request is invalid.", err)
                }
            }

        return ServerResponse.noContent().build()
    }

    fun register(request: ServerRequest): Mono<out ServerResponse> {
        val func = request.bodyToMono(PersonRequest::class.java)
            .map {
                saveUseCase.register(
                    name = it.name,
                    age = it.age
                ).throwIfLeft { err ->
                    InvalidRequestException("Request is invalid.", err)
                }
            }

        return ServerResponse.ok().body(func, String::class.java)
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