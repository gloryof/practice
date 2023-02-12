package jp.glory.open_feature.practice.product.domain.repository

import jp.glory.open_feature.practice.product.domain.model.Service
import jp.glory.open_feature.practice.product.domain.model.ServiceID

interface ServiceRepository {
    fun findById(id: ServiceID): Service?
    fun findAll(): List<Service>
}