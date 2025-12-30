package jp.glory.practice.boot.app.user

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
    webFunction()

    registerBean {
        webRouting(
            userCreateRouter = bean()
        )
    }
}) {

    companion object {
        fun webRouting(
            userCreateRouter: UserCreateRouter
        ) = router {
            val pathBase = "/api/v1/user"
            accept(APPLICATION_JSON).nest {
                POST(pathBase, userCreateRouter::create)
            }
        }
    }
}

private fun BeanRegistrarDsl.dao() {
    registerBean<UserDao>()
}

private fun BeanRegistrarDsl.eventHandlers() {
    registerBean<UserEventHandlerImpl>()
}

private fun BeanRegistrarDsl.domainService() {
    registerBean<UserIdGenerator>()
    registerBean<UserService>()
}

private fun BeanRegistrarDsl.repository() {
    registerBean<UserRepositoryImpl>()
}

private fun BeanRegistrarDsl.usecase() {
    registerBean<CreateUser>()
}

private fun BeanRegistrarDsl.webFunction() {
    registerBean<UserCreateRouter>()
}
