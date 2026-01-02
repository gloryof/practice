package jp.glory.practice.boot.app.test.tool

import io.mockk.mockk
import jp.glory.practice.boot.app.auth.AuthBeanRegister
import jp.glory.practice.boot.app.auth.command.usecase.IssueToken
import jp.glory.practice.boot.app.auth.command.web.LoginRouter
import jp.glory.practice.boot.app.user.UserBeanRegister
import jp.glory.practice.boot.app.user.command.usecase.CreateUser
import jp.glory.practice.boot.app.user.command.web.UserCreateRouter
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.function.RouterFunction
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.json.JsonMapper
import java.time.Clock


class MockMvcCreator(
    private val clock: Clock = Clock.systemDefaultZone()
) {
    private val functions: MutableList<RouterFunction<*>> = mutableListOf()

    init {
        val validator = LocalValidatorFactoryBean()
        validator.afterPropertiesSet()
    }

    fun create(): MockMvc {
        val builder = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

        return MockMvcBuilders
            .routerFunctions(*functions.toTypedArray())
            .setMessageConverters(JacksonJsonHttpMessageConverter(builder))
            .build()
    }

    fun activateUserRoute(
        createUser: CreateUser = mockk()
    ) {
        val router = UserCreateRouter(
            usecase = createUser,
            clock = clock
        )
        UserBeanRegister.webRouting(
            userCreateRouter = router
        )
            .let { functions.add(it) }
    }

    fun activateAuthRoute(
        issueToken: IssueToken = mockk()
    ) {
        val router = LoginRouter(
            issueToken = issueToken
        )
        AuthBeanRegister.webRouting(
            loginRouter = router
        )
            .let { functions.add(it) }
    }

}
