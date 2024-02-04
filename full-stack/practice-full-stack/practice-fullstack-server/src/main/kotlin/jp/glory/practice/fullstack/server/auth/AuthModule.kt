package jp.glory.practice.fullstack.server.auth

import jp.glory.practice.fullstack.server.auth.adaptor.event.AuthorizedEventHandlerImpl
import jp.glory.practice.fullstack.server.auth.adaptor.store.AuthorizedUserRepositoryImpl
import jp.glory.practice.fullstack.server.auth.adaptor.store.RegisteredUserRepositoryImpl
import jp.glory.practice.fullstack.server.auth.adaptor.web.LoginApi
import jp.glory.practice.fullstack.server.auth.domain.AuthorizedEventHandler
import jp.glory.practice.fullstack.server.auth.domain.AuthorizedUserRepository
import jp.glory.practice.fullstack.server.auth.domain.RegisteredUserRepository
import jp.glory.practice.fullstack.server.auth.usecase.GetAuthorizedUser
import jp.glory.practice.fullstack.server.auth.usecase.Login
import jp.glory.practice.fullstack.server.auth.usecase.Logout
import jp.glory.practice.fullstack.server.review.adaptor.store.ReviewRepositoryImpl
import jp.glory.practice.fullstack.server.review.domain.ReviewRepository
import jp.glory.practice.fullstack.server.review.usecase.GetUserReviews
import jp.glory.practice.fullstack.server.user.adaptor.store.UserRepositoryImpl
import jp.glory.practice.fullstack.server.user.domain.UserRepository
import jp.glory.practice.fullstack.server.user.usecase.GetUser
import jp.glory.practice.fullstack.server.user.usecase.RegisterUser
import jp.glory.practice.fullstack.server.user.usecase.UpdateUser
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object AuthModule {
    fun createModule(): Module = module {
        // Event
        singleOf(::AuthorizedEventHandlerImpl) { bind<AuthorizedEventHandler>() }

        // Store
        singleOf(::AuthorizedUserRepositoryImpl) { bind<AuthorizedUserRepository>() }
        singleOf(::RegisteredUserRepositoryImpl) { bind<RegisteredUserRepository>() }

        // Usecase
        singleOf(::GetAuthorizedUser)
        singleOf(::Login)
        singleOf(::Logout)

        // Web
        singleOf(::LoginApi)
    }
}