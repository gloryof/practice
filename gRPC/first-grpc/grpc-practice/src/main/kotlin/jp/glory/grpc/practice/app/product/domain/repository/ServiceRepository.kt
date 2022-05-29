package jp.glory.grpc.practice.app.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.grpc.practice.base.domain.DomainUnknownError
import jp.glory.grpc.practice.app.product.domain.model.Service
import jp.glory.grpc.practice.app.product.domain.model.ServiceID

interface ServiceRepository {
    fun findById(id: ServiceID): Result<Service?, DomainUnknownError>
    fun findByIds(ids: List<ServiceID>): Result<List<Service>, DomainUnknownError>
}
