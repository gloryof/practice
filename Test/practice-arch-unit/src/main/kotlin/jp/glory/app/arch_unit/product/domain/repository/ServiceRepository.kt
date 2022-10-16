package jp.glory.app.arch_unit.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.app.arch_unit.base.domain.DomainUnknownError
import jp.glory.app.arch_unit.product.domain.model.Service
import jp.glory.app.arch_unit.product.domain.model.ServiceID

interface ServiceRepository {
    fun findById(id: ServiceID): Result<Service?, DomainUnknownError>
    fun findAll(): Result<List<Service>, DomainUnknownError>
}
