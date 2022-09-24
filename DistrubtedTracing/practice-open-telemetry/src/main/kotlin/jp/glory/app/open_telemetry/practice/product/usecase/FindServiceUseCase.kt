package jp.glory.app.open_telemetry.practice.product.usecase

import com.github.michaelbull.result.*
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseError
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseNotFoundError
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseTelemetry
import jp.glory.app.open_telemetry.practice.base.usecase.toUseCaseError
import jp.glory.app.open_telemetry.practice.product.domain.model.Service
import jp.glory.app.open_telemetry.practice.product.domain.model.ServiceID
import jp.glory.app.open_telemetry.practice.product.domain.repository.ServiceRepository
import jp.glory.app.open_telemetry.practice.product.domain.model.ServiceKind as DomainServiceKind

class FindServiceUseCase(
    private val repository: ServiceRepository,
    private val useCaseTelemetry: UseCaseTelemetry
) {
    fun findById(id: String): Result<ServiceSearchResult, UseCaseError> {
        registerTelemetry("findById")
        return repository.findById(ServiceID(id))
            .mapBoth(
                success = {
                    if (it == null) {
                        createNotFound(id)
                    } else {
                        Ok(toResult(it))
                    }
                },
                failure = { Err(toUseCaseError(it)) }
            )
    }

    fun findAll(): Result<ServiceSearchResults, UseCaseError> {
        registerTelemetry("findAll")
        return repository.findAll()
            .map { toResults(it) }
            .mapError { toUseCaseError(it) }
    }

    private fun registerTelemetry(methodName: String) {
        useCaseTelemetry.registerUseCaseAttribute(
            useCaseName = "findService",
            methodName = methodName
        )
    }

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
