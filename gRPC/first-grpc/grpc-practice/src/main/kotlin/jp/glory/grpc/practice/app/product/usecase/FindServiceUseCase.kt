package jp.glory.grpc.practice.app.product.usecase

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.Result
import jp.glory.grpc.practice.base.usecase.UseCase
import jp.glory.grpc.practice.base.usecase.UseCaseError
import jp.glory.grpc.practice.base.usecase.toUseCaseError
import jp.glory.grpc.practice.app.product.domain.model.Service
import jp.glory.grpc.practice.app.product.domain.model.ServiceID
import jp.glory.grpc.practice.app.product.domain.repository.ServiceRepository
import jp.glory.grpc.practice.app.product.domain.model.ServiceKind as DomainServiceKind

@UseCase
class FindServiceUseCase(
    private val repository: ServiceRepository
) {
    fun findById(id: String): Result<ServiceSearchResult?, UseCaseError> =
        repository.findById(ServiceID(id))
            .map { toResult(it) }
            .mapError { toUseCaseError(it) }

    fun findByIds(ids: List<String>): Result<ServiceSearchResults, UseCaseError> =
        repository.findByIds(ids.map { ServiceID(it) })
            .map { toResults(it) }
            .mapError { toUseCaseError(it) }

    private fun toResults(Services: List<Service>): ServiceSearchResults =
        ServiceSearchResults(Services.map { ServiceSearchResult(it) })

    private fun toResult(Service: Service?): ServiceSearchResult? =
        Service?.let { ServiceSearchResult(it) }
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
            fun toResult(kind: DomainServiceKind) =
                when (kind) {
                    DomainServiceKind.Finance -> Finance
                    DomainServiceKind.Entertainment -> Entertainment
                    DomainServiceKind.HealthCare -> HealthCare
                }
        }
    }
}
