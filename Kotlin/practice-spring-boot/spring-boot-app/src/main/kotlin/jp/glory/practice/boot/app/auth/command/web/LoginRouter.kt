package jp.glory.practice.boot.app.auth.command.web

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.auth.command.domain.model.Password
import jp.glory.practice.boot.app.auth.command.usecase.IssueToken
import jp.glory.practice.boot.app.base.common.web.exception.WebErrorHandler
import jp.glory.practice.boot.app.base.common.web.exception.WebErrors
import jp.glory.practice.boot.app.base.common.web.request.RequestValidator
import jp.glory.practice.boot.app.base.common.web.request.WebRequestBody
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class LoginRouter(
    private val issueToken: IssueToken
) {
    fun login(request: ServerRequest): ServerResponse =
        request.body(Request::class.java)
            .parse()
            .flatMap { issueToken(it) }
            .mapBoth(
                success = { createSuccessResponse(it) },
                failure = { WebErrorHandler.createErrorResponse(it) }
            )

    private fun issueToken(input: IssueToken.Input): Result<String, WebErrors> =
        issueToken.issue(input)
            .mapError { WebErrors.fromUsecaseError(it) }

    private fun createSuccessResponse(token: String): ServerResponse =
        ServerResponse.ok()
            .body(Response(token))

    class Request(
        val loginId: String,
        val password: String
    ) : WebRequestBody<IssueToken.Input>() {
        override fun validate(): Result<Unit, WebErrors> =
            RequestValidator()
                .apply {
                    addValidator { LoginId.validate("login_id", loginId) }
                    addValidator { Password.validate("password", password) }
                }
                .validate()

        override fun convert(): IssueToken.Input =
            IssueToken.Input(
                loginId = loginId,
                password = password
            )
    }

    class Response(
        val token: String
    )
}
