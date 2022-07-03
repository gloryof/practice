package jp.glory.grpc.practice.app.product

import jp.glory.grpc.practice.app.product.adaptor.store.MemberRepositoryImpl
import jp.glory.grpc.practice.app.product.adaptor.store.ProductRepositoryImpl
import jp.glory.grpc.practice.app.product.adaptor.store.ServiceRepositoryImpl
import jp.glory.grpc.practice.app.product.adaptor.web.grpc.ProductGrpcService
import jp.glory.grpc.practice.app.product.domain.repository.MemberRepository
import jp.glory.grpc.practice.app.product.domain.repository.ProductRepository
import jp.glory.grpc.practice.app.product.domain.repository.ServiceRepository
import jp.glory.grpc.practice.app.product.usecase.*
import org.koin.dsl.module
import  org.koin.core.module.Module

object ProductModule {
    fun productModule(): Module = module {
        single< MemberRepository> { MemberRepositoryImpl() }
        single<ProductRepository> { ProductRepositoryImpl() }
        single<ServiceRepository> { ServiceRepositoryImpl() }

        single { FindMemberUseCase(get()) }
        single { FindProductUseCase(get()) }
        single { FindServiceUseCase(get()) }
        single { RegisterProductUseCase(get()) }
        single { UpdateProductUseCase(get()) }

        single { ProductGrpcService(get()) }
    }
}
