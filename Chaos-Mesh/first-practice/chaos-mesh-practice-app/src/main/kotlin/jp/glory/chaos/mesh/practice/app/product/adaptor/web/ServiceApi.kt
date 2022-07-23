package jp.glory.chaos.mesh.practice.app.product.adaptor.web

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import jp.glory.chaos.mesh.practice.app.base.adaptor.web.error.WebExceptionHelper
import jp.glory.chaos.mesh.practice.app.product.usecase.FindServiceUseCase
import jp.glory.chaos.mesh.practice.app.product.usecase.ServiceSearchResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/services")
class ServiceApi(
    private val findServiceUseCase: FindServiceUseCase,
) {
    @GetMapping
    fun findAll(): ResponseEntity<ServicesResponse> =
        findServiceUseCase.findAll()
            .map {
                ServicesResponse(
                    services = it.results.map { result -> toServiceResponse(result) }
                )
            }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: String
    ): ResponseEntity<ServiceResponse> =
        findServiceUseCase.findById(id)
            .map { toServiceResponse(it) }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    private fun toServiceResponse(
        result: ServiceSearchResult
    ): ServiceResponse = ServiceResponse(
        id = result.id,
        name = result.name,
        kind = ServiceResponse.ServiceKind.toResponse(result.kind)
    )
}

data class ServicesResponse(
    val services: List<ServiceResponse>
)

data class ServiceResponse(
    val id: String,
    val name: String,
    val kind: ServiceKind
) {
    enum class ServiceKind {
        Finance,
        Entertainment,
        HealthCare;

        companion object {
            fun toResponse(kind: ServiceSearchResult.ServiceKind): ServiceKind =
                when (kind) {
                    ServiceSearchResult.ServiceKind.Finance -> Finance
                    ServiceSearchResult.ServiceKind.Entertainment -> Entertainment
                    ServiceSearchResult.ServiceKind.HealthCare -> HealthCare
                }
        }
    }
}
