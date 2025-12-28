package jp.glory.practice.boot.app.user

import jp.glory.practice.boot.app.auth.data.AuthDao
import jp.glory.practice.boot.app.user.command.domain.event.UserEventHandler
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import jp.glory.practice.boot.app.user.command.domain.service.UserService
import jp.glory.practice.boot.app.user.command.infra.event.UserEventHandlerImpl
import jp.glory.practice.boot.app.user.command.infra.reposiotry.UserRepositoryImpl
import jp.glory.practice.boot.app.user.command.usecase.CreateUser
import jp.glory.practice.boot.app.user.command.web.UserCreateRouter
import jp.glory.practice.boot.app.user.data.UserDao
import org.springframework.beans.factory.BeanRegistrarDsl
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.servlet.function.router

/**
 * Required
 * - AuthBeanRegister
 */
class UserBeanRegister : BeanRegistrarDsl({
    dao()
    eventHandlers()
    domainService()
    repository()
    usecase()
    webRouting()
})

private fun BeanRegistrarDsl.webRouting() {
    registerBean<UserCreateRouter>()

    registerBean {
        val pathBase = "/api/v1/user"

        val userCreateRouter: UserCreateRouter = bean()
        router {
            accept(APPLICATION_JSON).nest {
                POST(pathBase, userCreateRouter::create)
            }
        }
    }
}

private fun BeanRegistrarDsl.eventHandlers() {
    registerBean<UserEventHandler> {
        UserEventHandlerImpl(
            userDao = bean<UserDao>(),
            authDao = bean<AuthDao>()
        )
    }
}

private fun BeanRegistrarDsl.domainService() {
    registerBean<UserIdGenerator>()
    registerBean<UserService>()
}

private fun BeanRegistrarDsl.usecase() {
    registerBean<CreateUser>()
}

private fun BeanRegistrarDsl.repository() {
    registerBean<UserRepositoryImpl>()
}

private fun BeanRegistrarDsl.dao() {
    registerBean<UserDao>()
}
