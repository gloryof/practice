package jp.glory.practice.boot.app.user.command.web

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import jp.glory.practice.boot.app.base.web.RequestValidator
import jp.glory.practice.boot.app.base.web.WebErrorHandler
import jp.glory.practice.boot.app.base.web.WebErrors
import jp.glory.practice.boot.app.base.web.WebRequestBodyWithClock
import jp.glory.practice.boot.app.user.command.domain.model.Birthday
import jp.glory.practice.boot.app.user.command.domain.model.LoginId
import jp.glory.practice.boot.app.user.command.domain.model.Password
import jp.glory.practice.boot.app.user.command.domain.model.UserName
import jp.glory.practice.boot.app.user.command.usecase.CreateUser
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.time.Clock
import java.time.LocalDate

class UserCreateRouter(
    private val usecase: CreateUser,
    private val clock: Clock
) {

    fun create(request: ServerRequest): ServerResponse =
        request.body(Request::class.java)
            .parse(clock)
            .flatMap { createUser(it) }
            .mapBoth(
                success = { createSuccessResponse(it) },
                failure = { WebErrorHandler.createErrorResponse(it) }
            )

    private fun createUser(input: CreateUser.Input): Result<String, WebErrors> =
        usecase.createUser(input)
            .mapError { WebErrors.fromUsecaseError(it) }

    private fun createSuccessResponse(id: String): ServerResponse =
        ServerResponse.ok()
            .body(Response(id))

    class Request(
        private val loginId: String,
        private val userName: String,
        private val password: String,
        private val birthday: LocalDate
    ) : WebRequestBodyWithClock<CreateUser.Input>() {

        override fun validate(clock: Clock): Result<Unit, WebErrors> =
            RequestValidator()
                .apply {
                    addValidator { LoginId.validate("login_id", loginId) }
                    addValidator { UserName.validate("user_name", userName) }
                    addValidator { Password.validate("password", password) }
                    addValidator { Birthday.validate("birthday", birthday, LocalDate.now(clock)) }
                }
                .validate()

        override fun convert(): CreateUser.Input {
            return CreateUser.Input(
                loginId = loginId,
                userName = userName,
                password = password,
                birthday = birthday
            )
        }
    }

    data class Response(
        val userId: String
    )
}
