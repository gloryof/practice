package jp.glory.jfr.practice.app.product.usecase

import com.github.michaelbull.result.*
import jp.glory.jfr.practice.app.base.usecase.UseCase
import jp.glory.jfr.practice.app.base.usecase.UseCaseError
import jp.glory.jfr.practice.app.base.usecase.UseCaseNotFoundError
import jp.glory.jfr.practice.app.base.usecase.toUseCaseError
import jp.glory.jfr.practice.app.product.domain.model.Service
import jp.glory.jfr.practice.app.product.domain.model.ServiceID
import jp.glory.jfr.practice.app.product.domain.repository.ServiceRepository
import jp.glory.jfr.practice.app.product.domain.model.ServiceKind as DomainServiceKind

@UseCase
class FindServiceUseCase(
    private val repository: ServiceRepository
) {
    fun findById(id: String): Result<ServiceSearchResult, UseCaseError> =
        repository.findById(ServiceID(id))
            .mapBoth (
                success = {
                    if (it == null) {
                        createNotFound(id)
                    } else {
                        Ok(toResult(it))
                    }
                },
                failure = { Err(toUseCaseError(it)) }
            )

    fun findAll(): Result<ServiceSearchResults, UseCaseError> =
        repository.findAll()
            .map { toResults(it) }
            .mapError { toUseCaseError(it) }

    private fun toResults(services: List<Service>): ServiceSearchResults =
        ServiceSearchResults(services.map { ServiceSearchResult(it) })

    private fun toResult(service: Service): ServiceSearchResult =
        ServiceSearchResult(service)

    private fun createNotFound(id: String): Err<UseCaseNotFoundError> =
        Err(
            UseCaseNotFoundError(
                resourceName = UseCaseNotFoundError.ResourceName.Product,
                idValue = id
            )
        )
}

data class ServiceSearchResults(
    val results: List<ServiceSearchResult>
)

data class ServiceSearchResult(
    val id: String,
    val name: String,
    val kind: ServiceKind
) {
    constructor(service: Service) : this(
        id = service.id.value,
        name = service.name.value,
        kind = ServiceKind.toResult(service.kind)
    )

    enum class ServiceKind {
        Finance,
        Entertainment,
        HealthCare;

        companion object {
            fun toResult(kind: DomainServiceKind): ServiceKind =
                when (kind) {
                    DomainServiceKind.Finance -> Finance
                    DomainServiceKind.Entertainment -> Entertainment
                    DomainServiceKind.HealthCare -> HealthCare
                }
        }
    }
}
