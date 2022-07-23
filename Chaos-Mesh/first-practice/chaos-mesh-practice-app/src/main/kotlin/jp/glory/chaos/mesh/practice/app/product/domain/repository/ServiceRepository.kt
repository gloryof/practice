package jp.glory.chaos.mesh.practice.app.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.chaos.mesh.practice.app.base.domain.DomainUnknownError
import jp.glory.chaos.mesh.practice.app.product.domain.model.Service
import jp.glory.chaos.mesh.practice.app.product.domain.model.ServiceID

interface ServiceRepository {
    fun findById(id: ServiceID): Result<Service?, DomainUnknownError>
    fun findAll(): Result<List<Service>, DomainUnknownError>
}
