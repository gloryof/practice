package jp.glory.open_feature.practice.product.adaptor.web

import com.github.michaelbull.result.mapBoth
import jp.glory.open_feature.practice.base.adaptor.web.EndpointConst
import jp.glory.open_feature.practice.base.adaptor.web.WebApi
import jp.glory.open_feature.practice.base.adaptor.web.WebExceptionHelper
import jp.glory.open_feature.practice.product.use_case.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@WebApi
@RequestMapping(EndpointConst.Product.service)
class ServiceApi(
    private val findService: FindServiceUseCase
) {

    @GetMapping
    fun findAllServices(): Services =
        findService.findAll()
            .let { it.results.map { service -> Service(service) } }
            .let { Services(it) }

    @GetMapping("/{id}")
    fun findService(
        @PathVariable("id") id: String,
    ): Service =
        findService.findById(id)
            .mapBoth(
                success = { Service(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    class Services(
        val services: List<Service>
    )

    data class Service(
        val id: String,
        val name: String,
        val kind: ServiceKind
    ) {
        constructor(result: ServiceSearchResult) : this(
            id = result.id,
            name = result.name,
            kind = when (result.kind) {
                ServiceSearchResult.ServiceKind.Finance -> ServiceKind.Finance
                ServiceSearchResult.ServiceKind.Entertainment -> ServiceKind.Entertainment
                ServiceSearchResult.ServiceKind.HealthCare -> ServiceKind.HealthCare
            }
        )
    }

    enum class ServiceKind {
        Finance,
        Entertainment,
        HealthCare
    }
}
