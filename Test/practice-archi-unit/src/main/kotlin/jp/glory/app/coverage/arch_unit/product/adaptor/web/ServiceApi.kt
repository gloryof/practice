package jp.glory.app.coverage.arch_unit.product.adaptor.web

import com.github.michaelbull.result.*
import jp.glory.app.coverage.arch_unit.base.adaptor.web.error.WebError
import jp.glory.app.coverage.arch_unit.base.adaptor.web.error.WebErrorHelper
import jp.glory.app.coverage.arch_unit.product.usecase.FindServiceUseCase
import jp.glory.app.coverage.arch_unit.product.usecase.ServiceSearchResult
import org.jetbrains.exposed.sql.transactions.transaction

class ServiceApi(
    private val findServiceUseCase: FindServiceUseCase,
) {
    fun findAll(): Result<ServicesResponse, WebError> =
        transaction {
            findServiceUseCase.findAll()
                .map {
                    ServicesResponse(
                        services = it.results.map { result -> toServiceResponse(result) }
                    )
                }
                .mapBoth(
                    success = { Ok(it) },
                    failure = { Err(WebErrorHelper.create(it)) }
                )
        }

    fun findById(
        id: String
    ): Result<ServiceResponse, WebError> =
        transaction {
            findServiceUseCase.findById(id)
                .map { toServiceResponse(it) }
                .mapBoth(
                    success = { Ok(it) },
                    failure = { Err(WebErrorHelper.create(it)) }
                )
        }

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
