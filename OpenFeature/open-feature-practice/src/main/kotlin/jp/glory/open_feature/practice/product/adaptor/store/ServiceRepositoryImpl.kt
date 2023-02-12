package jp.glory.open_feature.practice.product.adaptor.store

import jp.glory.open_feature.practice.product.domain.model.Service
import jp.glory.open_feature.practice.product.domain.model.ServiceID
import jp.glory.open_feature.practice.product.domain.model.ServiceKind
import jp.glory.open_feature.practice.product.domain.model.ServiceName
import jp.glory.open_feature.practice.product.domain.repository.ServiceRepository
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
    ): Service? = services[id.value]

    override fun findAll(): List<Service> =
        services.values.toList()
}