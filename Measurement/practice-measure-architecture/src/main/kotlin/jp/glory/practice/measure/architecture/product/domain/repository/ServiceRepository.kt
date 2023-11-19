package jp.glory.practice.measure.architecture.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.practice.measure.architecture.base.domain.DomainUnknownError
import jp.glory.practice.measure.architecture.product.domain.model.Service
import jp.glory.practice.measure.architecture.product.domain.model.ServiceID

interface ServiceRepository {
    fun findById(id: ServiceID): Result<Service?, DomainUnknownError>
    fun findByIds(ids: List<ServiceID>): Result<List<Service>, DomainUnknownError>
}