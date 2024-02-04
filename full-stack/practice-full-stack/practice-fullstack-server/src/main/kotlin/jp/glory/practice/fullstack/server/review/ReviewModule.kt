package jp.glory.practice.fullstack.server.review

import jp.glory.practice.fullstack.server.review.adaptor.event.ReviewEventHandlerImpl
import jp.glory.practice.fullstack.server.review.adaptor.store.ReviewRepositoryImpl
import jp.glory.practice.fullstack.server.review.domain.ReviewEventHandler
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

object ReviewModule {
    fun createModule(): Module = module {
        // Evnet
        singleOf(::ReviewEventHandlerImpl) { bind<ReviewEventHandler>() }

        // Store
        singleOf(::ReviewRepositoryImpl) { bind<ReviewRepository>() }

        // Usecase
        singleOf(::GetUserReviews)
        singleOf(::RegisterUser)
        singleOf(::UpdateUser)
    }
}