package jp.glory.practicegraphql.app.product.usecase

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import jp.glory.practicegraphql.app.base.usecase.UseCaseError
import jp.glory.practicegraphql.app.base.usecase.toUseCaseError
import jp.glory.practicegraphql.app.product.domain.model.Service
import jp.glory.practicegraphql.app.product.domain.model.ServiceKind as DomainServiceKind
import jp.glory.practicegraphql.app.product.domain.model.ServiceID
import jp.glory.practicegraphql.app.product.domain.repository.ServiceRepository

class FindServiceUseCase(
    private val repository: ServiceRepository
) {
    fun findById(id: String): Result<SearchServiceResult?, UseCaseError> =
        repository.findById(ServiceID(id))
            .map { toResult(it) }
            .mapError { toUseCaseError(it) }

    fun findByIds(ids: List<String>): Result<SearchServiceResults, UseCaseError> =
        repository.findByIds(ids.map { ServiceID(it) })
            .map { toResults(it) }
            .mapError { toUseCaseError(it) }

    private fun toResults(Services: List<Service>): SearchServiceResults =
        SearchServiceResults(Services.map { SearchServiceResult(it) })

    private fun toResult(Service: Service?): SearchServiceResult? =
        Service?.let{ SearchServiceResult(it) }
}

data class SearchServiceResults(
    val results: List<SearchServiceResult>
)
data class SearchServiceResult(
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
                when(kind) {
                    DomainServiceKind.Finance -> Finance
                    DomainServiceKind.Entertainment -> Entertainment
                    DomainServiceKind.HealthCare -> HealthCare
                }
        }
    }
}