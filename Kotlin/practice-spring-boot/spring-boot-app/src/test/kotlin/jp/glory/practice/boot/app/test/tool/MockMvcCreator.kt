package jp.glory.practice.boot.app.test.tool

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import io.mockk.every
import io.mockk.mockk
import jp.glory.practice.boot.app.auth.AuthBeanRegister
import jp.glory.practice.boot.app.auth.command.usecase.IssueToken
import jp.glory.practice.boot.app.auth.command.web.LoginRouter
import jp.glory.practice.boot.app.auth.query.usecase.Authenticate
import jp.glory.practice.boot.app.auth.query.web.AuthenticateFilter
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseErrors
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseSpecErrorType
import jp.glory.practice.boot.app.user.UserBeanRegister
import jp.glory.practice.boot.app.user.command.usecase.CreateUser
import jp.glory.practice.boot.app.user.command.web.UserCreateRouter
import jp.glory.practice.boot.app.user.query.usecase.GetUser
import jp.glory.practice.boot.app.user.query.web.GetOwnRouter
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.function.RouterFunction
import java.time.Clock
import java.util.UUID


class MockMvcCreator(
    private val clock: Clock = Clock.systemDefaultZone()
) {
    private val functions: MutableList<RouterFunction<*>> = mutableListOf()
    private var authenticateFilter: AuthenticateFilter = mockk()

    init {
        val validator = LocalValidatorFactoryBean()
        validator.afterPropertiesSet()
    }

    fun create(): MockMvc {
        val builder = JsonMapperBuilderCreator.create()

        return MockMvcBuilders
            .routerFunctions(*functions.toTypedArray())
            .setMessageConverters(JacksonJsonHttpMessageConverter(builder))
            .build()
    }

    fun activateUserRoute(
        createUser: CreateUser = mockk(),
        getUser: GetUser = mockk()
    ) {
        val userCreateRouter = UserCreateRouter(
            usecase = createUser,
            clock = clock
        )
        val getOwnRouter = GetOwnRouter(
            usecase = getUser
        )
        UserBeanRegister.webRouting(
            userCreateRouter = userCreateRouter,
            getOwnRouter = getOwnRouter,
            authenticateFilter = authenticateFilter
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

    fun activateSuccessAuthenticate(
        accessToken: String = UUID.randomUUID().toString(),
        userId: String = UUID.randomUUID().toString()
    ) {
        val authenticate: Authenticate = mockk()
        every { authenticate.authenticate(accessToken) } returns Ok(
            Authenticate.Output(
                userId = userId
            )
        )
        authenticateFilter = AuthenticateFilter(authenticate)
    }

    fun activateFailAuthenticate() {
        val authenticate: Authenticate = mockk()
        every { authenticate.authenticate(any()) } returns Err(
            UsecaseErrors(
                specErrors = listOf(UsecaseSpecErrorType.UNAUTHORIZED)
            )
        )
        authenticateFilter = AuthenticateFilter(authenticate)
    }
}
