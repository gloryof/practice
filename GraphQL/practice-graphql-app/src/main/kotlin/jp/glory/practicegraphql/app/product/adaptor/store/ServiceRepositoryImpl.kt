package jp.glory.practicegraphql.app.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.product.domain.model.Service
import jp.glory.practicegraphql.app.product.domain.model.ServiceID
import jp.glory.practicegraphql.app.product.domain.model.ServiceKind
import jp.glory.practicegraphql.app.product.domain.model.ServiceName
import jp.glory.practicegraphql.app.product.domain.repository.ServiceRepository
import org.springframework.stereotype.Repository

@Repository
class ServiceRepositoryImpl : ServiceRepository {
    private val services: MutableMap<String, Service> = mutableMapOf()

    init {
        repeat(10) {
            val id = "service-id-$it"
            val service = Service(
                id = ServiceID("service-id-$it"),
                name = ServiceName("service-name-$it"),
                kind = when (it % 3) {
                    1 -> ServiceKind.Finance
                    2 -> ServiceKind.Entertainment
                    else -> ServiceKind.HealthCare
                }
            )
            services[id] = service
        }
    }

    override fun findById(
        id: ServiceID
    ): Result<Service?, DomainUnknownError> = Ok(services[id.value])

    override fun findByIds(ids: List<ServiceID>): Result<List<Service>, DomainUnknownError> {
        println("Find ids[$ids]")
        return services
            .filter { (key, _) -> ids.contains(ServiceID(key)) }
            .map { it.value }
            .let { Ok(it) }
    }
}
