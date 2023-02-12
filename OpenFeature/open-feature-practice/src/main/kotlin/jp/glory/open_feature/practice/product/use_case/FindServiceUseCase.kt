package jp.glory.open_feature.practice.product.use_case

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.open_feature.practice.base.use_case.UseCase
import jp.glory.open_feature.practice.base.use_case.UseCaseError
import jp.glory.open_feature.practice.base.use_case.UseCaseNotFoundError
import jp.glory.open_feature.practice.product.domain.model.Service
import jp.glory.open_feature.practice.product.domain.model.ServiceID
import jp.glory.open_feature.practice.product.domain.repository.ServiceRepository
import jp.glory.open_feature.practice.product.domain.model.ServiceKind as DomainServiceKind

@UseCase
class FindServiceUseCase(
    private val repository: ServiceRepository
) {
    fun findById(id: String): Result<ServiceSearchResult, UseCaseError> =
        repository.findById(ServiceID(id))
            ?.let { Ok(ServiceSearchResult(it)) }
            ?: createNotFound(id)

    fun findAll(): ServiceSearchResults =
        repository.findAll()
            .let { toResults(it) }

    private fun toResults(Services: List<Service>): ServiceSearchResults =
        ServiceSearchResults(Services.map { ServiceSearchResult(it) })

    private fun toResult(Service: Service?): ServiceSearchResult? =
        Service?.let { ServiceSearchResult(it) }

    private fun createNotFound(id: String): Err<UseCaseNotFoundError> =
        Err(
            UseCaseNotFoundError(
                resourceName = UseCaseNotFoundError.ResourceName.Service,
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
            fun toResult(kind: DomainServiceKind) =
                when (kind) {
                    DomainServiceKind.Finance -> Finance
                    DomainServiceKind.Entertainment -> Entertainment
                    DomainServiceKind.HealthCare -> HealthCare
                }
        }
    }
}