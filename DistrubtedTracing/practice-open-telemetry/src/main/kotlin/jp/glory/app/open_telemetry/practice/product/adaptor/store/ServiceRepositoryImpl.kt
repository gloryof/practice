package jp.glory.app.open_telemetry.practice.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.app.open_telemetry.practice.base.domain.DomainUnknownError
import jp.glory.app.open_telemetry.practice.product.domain.model.Service
import jp.glory.app.open_telemetry.practice.product.domain.model.ServiceID
import jp.glory.app.open_telemetry.practice.product.domain.model.ServiceKind
import jp.glory.app.open_telemetry.practice.product.domain.model.ServiceName
import jp.glory.app.open_telemetry.practice.product.domain.repository.ServiceRepository

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

    override fun findAll(): Result<List<Service>, DomainUnknownError> {
        return services
            .map { it.value }
            .let { Ok(it) }
    }
}
