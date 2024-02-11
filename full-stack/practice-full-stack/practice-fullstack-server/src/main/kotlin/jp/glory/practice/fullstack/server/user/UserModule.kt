package jp.glory.practice.fullstack.server.user

import jp.glory.practice.fullstack.server.user.adaptor.event.UserEventHandlerImpl
import jp.glory.practice.fullstack.server.user.adaptor.grqphql.UserQuery
import jp.glory.practice.fullstack.server.user.adaptor.store.UserRepositoryImpl
import jp.glory.practice.fullstack.server.user.adaptor.web.RegisterUserApi
import jp.glory.practice.fullstack.server.user.domain.UserEventHandler
import jp.glory.practice.fullstack.server.user.domain.UserRepository
import jp.glory.practice.fullstack.server.user.usecase.GetUser
import jp.glory.practice.fullstack.server.user.usecase.RegisterUser
import jp.glory.practice.fullstack.server.user.usecase.UpdateUser
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object UserModule {
    fun createModule(): Module = module {
        // Event
        singleOf(::UserEventHandlerImpl) { bind<UserEventHandler>() }

        // Store
        singleOf(::UserRepositoryImpl) { bind<UserRepository>() }

        // Usecase
        singleOf(::GetUser)
        singleOf(::RegisterUser)
        singleOf(::UpdateUser)

        // Web
        singleOf(::RegisterUserApi)

        // GraphQL
        singleOf(::UserQuery)
    }
}