package jp.glory.app.open_telemetry.practice.product

import jp.glory.app.open_telemetry.practice.product.adaptor.store.MemberRepositoryImpl
import jp.glory.app.open_telemetry.practice.product.adaptor.store.ProductRepositoryImpl
import jp.glory.app.open_telemetry.practice.product.adaptor.store.ServiceRepositoryImpl
import jp.glory.app.open_telemetry.practice.product.adaptor.web.MemberApi
import jp.glory.app.open_telemetry.practice.product.adaptor.web.ProductApi
import jp.glory.app.open_telemetry.practice.product.adaptor.web.ServiceApi
import jp.glory.app.open_telemetry.practice.product.domain.repository.MemberRepository
import jp.glory.app.open_telemetry.practice.product.domain.repository.ProductRepository
import jp.glory.app.open_telemetry.practice.product.domain.repository.ServiceRepository
import jp.glory.app.open_telemetry.practice.product.usecase.*
import org.koin.core.module.Module
import org.koin.dsl.module

object ProductModule {
    fun productModule(): Module = module {
        single<MemberRepository> { MemberRepositoryImpl() }
        single<ProductRepository> { ProductRepositoryImpl() }
        single<ServiceRepository> { ServiceRepositoryImpl() }

        single { FindMemberUseCase(get(), get()) }
        single { FindProductUseCase(get(), get()) }
        single { FindServiceUseCase(get(), get()) }
        single { RegisterProductUseCase(get(), get()) }
        single { UpdateProductUseCase(get(), get()) }

        single { MemberApi(get()) }
        single { ProductApi(get(), get(), get()) }
        single { ServiceApi(get()) }
    }
}