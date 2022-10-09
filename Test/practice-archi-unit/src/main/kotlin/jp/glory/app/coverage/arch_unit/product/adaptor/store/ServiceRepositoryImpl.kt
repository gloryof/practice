package jp.glory.app.coverage.arch_unit.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.app.coverage.arch_unit.base.domain.DomainUnknownError
import jp.glory.app.coverage.arch_unit.product.domain.model.*
import jp.glory.app.coverage.arch_unit.product.domain.repository.ServiceRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ServiceRepositoryImpl : ServiceRepository {

    override fun findById(
        id: ServiceID
    ): Result<Service?, DomainUnknownError> =
        ServiceTable
            .select { ServiceTable.id eq id.value }
            .firstOrNull()
            ?.let { toDomainModel(it) }
            .let { Ok(it) }

    override fun findAll(): Result<List<Service>, DomainUnknownError> =
        ServiceTable.selectAll()
            .map { toDomainModel(it) }
            .let { Ok(it) }

    private fun toDomainModel(row: ResultRow): Service =
        Service(
            id = ServiceID(row[ServiceTable.id]),
            name = ServiceName(row[ServiceTable.name]),
            kind = toServiceKind(row[ServiceTable.kind])
        )

    private fun toServiceKind(value: Int): ServiceKind =
        when (value % 3) {
            1 -> ServiceKind.Finance
            2 -> ServiceKind.Entertainment
            else -> ServiceKind.HealthCare
        }
}
