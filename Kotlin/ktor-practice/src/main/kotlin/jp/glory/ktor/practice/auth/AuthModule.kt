package jp.glory.ktor.practice.auth

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import jp.glory.ktor.practice.auth.adaptor.store.dao.AuthenticationDao
import jp.glory.ktor.practice.auth.adaptor.store.dao.TokenDao
import jp.glory.ktor.practice.auth.adaptor.store.repository.CredentialRepositoryImpl
import jp.glory.ktor.practice.auth.adaptor.store.repository.TokenRepositoryImpl
import jp.glory.ktor.practice.auth.adaptor.web.AuthApi
import jp.glory.ktor.practice.auth.domain.repository.CredentialRepository
import jp.glory.ktor.practice.auth.domain.repository.TokenRepository
import jp.glory.ktor.practice.auth.use_case.AuthenticateUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.ext.inject

object AuthModule {
    fun createModule() = module {
        dao()
        repository()
        useCase()
        webApi()
    }

    fun routing(route: Route) {
        route.apply {
            val authApi by inject<AuthApi>()

            route {
                post("/auth") {
                    val request = call.receive<AuthApi.AuthRequest>()
                    authApi.authenticate(request)
                        .let { call.respond(it) }
                }
            }
        }
    }

    private fun Module.repository() {
        singleOf(::CredentialRepositoryImpl) { bind<CredentialRepository>() }
        singleOf(::TokenRepositoryImpl) { bind<TokenRepository>() }
    }

    private fun Module.useCase() {
        singleOf(::AuthenticateUseCase)
    }

    private fun Module.webApi() {
        singleOf(::AuthApi)
    }

    private fun Module.dao() {
        singleOf(::AuthenticationDao)
        singleOf(::TokenDao)
    }

}
