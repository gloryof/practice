package jp.glory.practice.fullstack.server.review

import jp.glory.practice.fullstack.server.review.adaptor.event.ReviewEventHandlerImpl
import jp.glory.practice.fullstack.server.review.adaptor.graphql.ReviewResolvers
import jp.glory.practice.fullstack.server.review.adaptor.graphql.mutation.RegisterReviewPayloadResolver
import jp.glory.practice.fullstack.server.review.adaptor.graphql.mutation.ReviewMutation
import jp.glory.practice.fullstack.server.review.adaptor.graphql.mutation.UpdateReviewPayloadResolver
import jp.glory.practice.fullstack.server.review.adaptor.graphql.query.ReviewQuery
import jp.glory.practice.fullstack.server.review.adaptor.store.ReviewRepositoryImpl
import jp.glory.practice.fullstack.server.review.domain.ReviewEventHandler
import jp.glory.practice.fullstack.server.review.domain.ReviewRepository
import jp.glory.practice.fullstack.server.review.usecase.GetUserReviews
import jp.glory.practice.fullstack.server.review.usecase.RegisterReview
import jp.glory.practice.fullstack.server.review.usecase.UpdateReview
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
        singleOf(::RegisterReview)
        singleOf(::UpdateReview)

        // GraqphQL
        singleOf(::ReviewMutation)
        singleOf(::ReviewQuery)
        singleOf(::RegisterReviewPayloadResolver)
        singleOf(::UpdateReviewPayloadResolver)
        singleOf(::ReviewResolvers)
    }
}