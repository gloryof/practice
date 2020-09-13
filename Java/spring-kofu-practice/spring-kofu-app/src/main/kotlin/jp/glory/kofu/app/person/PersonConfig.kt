package jp.glory.kofu.app.person

import jp.glory.kofu.app.person.api.PersonApi
import jp.glory.kofu.app.person.store.PersonRepositoryImpl
import jp.glory.kofu.app.person.store.PersonSaveEventRepositoryImpl
import jp.glory.kofu.app.person.usecase.PersonSaveUseCase
import jp.glory.kofu.app.person.usecase.PersonSearchUseCase
import org.springframework.context.support.beans
import org.springframework.fu.kofu.configuration
import org.springframework.web.reactive.function.server.router

object PersonConfig {

    fun config() = configuration {
        beans {
            bean<PersonApi>()

            bean<PersonSaveUseCase>()
            bean<PersonSearchUseCase>()

            bean<PersonRepositoryImpl>()
            bean<PersonSaveEventRepositoryImpl>()

            bean(::router)
        }
    }

    private fun router(handler: PersonApi) = router {
        val basePath = "/api/person"
        GET("$basePath/{id}", handler::get)
        PUT("$basePath/{id}", handler::update)
        POST("$basePath", handler::register)
    }
}