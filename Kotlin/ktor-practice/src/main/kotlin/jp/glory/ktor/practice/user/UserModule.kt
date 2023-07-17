package jp.glory.ktor.practice.user

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import jp.glory.ktor.practice.base.adaptor.web.WebValidationException
import jp.glory.ktor.practice.user.adaptor.store.dao.UserDao
import jp.glory.ktor.practice.user.adaptor.store.repository.UserRegisterEventRepositoryImpl
import jp.glory.ktor.practice.user.adaptor.store.repository.UserRepositoryImpl
import jp.glory.ktor.practice.user.adaptor.web.RegisterUserApi
import jp.glory.ktor.practice.user.adaptor.web.UserApi
import jp.glory.ktor.practice.user.domain.repository.UserRegisterEventRepository
import jp.glory.ktor.practice.user.domain.repository.UserRepository
import jp.glory.ktor.practice.user.use_case.FindUserUseCase
import jp.glory.ktor.practice.user.use_case.RegisterUserUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.ext.inject

object UserModule {
    fun createModule() = module {
        dao()
        repository()
        useCase()
        webApi()
    }

    fun routing(route: Route) {
        route.apply {
            val registerUserApi by inject<RegisterUserApi>()
            val userApi by inject<UserApi>()

            route {
                post("/register") {
                    val request = call.receive<RegisterUserApi.UserRegisterRequest>()
                    registerUserApi.registerUser(request)
                        .let { call.respond(it) }
                }

                authenticate {
                    route("/users") {
                        get {
                            userApi.getUsers()
                                .let { call.respond(it) }
                        }
                        get("/{id}") {
                            val id = call.parameters["id"] ?: throw WebValidationException(listOf("id is required"))
                            userApi.getById(id)
                                .let { call.respond(it) }
                        }
                    }
                }
            }
        }
    }

    private fun Module.repository() {
        singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
        singleOf(::UserRegisterEventRepositoryImpl) { bind<UserRegisterEventRepository>() }
    }

    private fun Module.useCase() {
        singleOf(::FindUserUseCase)
        singleOf(::RegisterUserUseCase)
    }

    private fun Module.webApi() {
        singleOf(::RegisterUserApi)
        singleOf(::UserApi)
    }

    private fun Module.dao() {
        singleOf(::UserDao)
    }
}
