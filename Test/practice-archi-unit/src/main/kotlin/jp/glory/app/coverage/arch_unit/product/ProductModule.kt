package jp.glory.app.coverage.arch_unit.product

import jp.glory.app.coverage.arch_unit.product.adaptor.store.MemberRepositoryImpl
import jp.glory.app.coverage.arch_unit.product.adaptor.store.ProductRepositoryImpl
import jp.glory.app.coverage.arch_unit.product.adaptor.store.ServiceRepositoryImpl
import jp.glory.app.coverage.arch_unit.product.adaptor.web.MemberApi
import jp.glory.app.coverage.arch_unit.product.adaptor.web.ProductApi
import jp.glory.app.coverage.arch_unit.product.adaptor.web.ServiceApi
import jp.glory.app.coverage.arch_unit.product.domain.repository.MemberRepository
import jp.glory.app.coverage.arch_unit.product.domain.repository.ProductRepository
import jp.glory.app.coverage.arch_unit.product.domain.repository.ServiceRepository
import jp.glory.app.coverage.arch_unit.product.usecase.*
import org.koin.core.module.Module
import org.koin.dsl.module

object ProductModule {
    fun productModule(): Module = module {
        single<MemberRepository> { MemberRepositoryImpl() }
        single<ProductRepository> { ProductRepositoryImpl() }
        single<ServiceRepository> { ServiceRepositoryImpl() }

        single { FindMemberUseCase(get()) }
        single { FindProductUseCase(get()) }
        single { FindServiceUseCase(get()) }
        single { RegisterProductUseCase(get()) }
        single { UpdateProductUseCase(get()) }

        single { MemberApi(get()) }
        single { ProductApi(get(), get(), get()) }
        single { ServiceApi(get()) }
    }
}