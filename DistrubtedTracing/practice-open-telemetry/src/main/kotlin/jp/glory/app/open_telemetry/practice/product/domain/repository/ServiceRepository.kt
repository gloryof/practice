package jp.glory.app.open_telemetry.practice.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.app.open_telemetry.practice.base.domain.DomainUnknownError
import jp.glory.app.open_telemetry.practice.product.domain.model.Service
import jp.glory.app.open_telemetry.practice.product.domain.model.ServiceID

interface ServiceRepository {
    fun findById(id: ServiceID): Result<Service?, DomainUnknownError>
    fun findAll(): Result<List<Service>, DomainUnknownError>
}
