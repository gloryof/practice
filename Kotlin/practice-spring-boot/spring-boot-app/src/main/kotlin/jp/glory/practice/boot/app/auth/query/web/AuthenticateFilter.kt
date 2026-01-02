package jp.glory.practice.boot.app.auth.query.web

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import jp.glory.practice.boot.app.auth.query.usecase.Authenticate
import jp.glory.practice.boot.app.base.common.web.RequestAttributeKey
import jp.glory.practice.boot.app.base.common.web.WebErrorHandler
import jp.glory.practice.boot.app.base.common.web.WebErrors
import jp.glory.practice.boot.app.base.common.web.WebSpecErrorType
import org.springframework.web.servlet.function.HandlerFilterFunction
import org.springframework.web.servlet.function.HandlerFunction
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class AuthenticateFilter(
    private val authenticate: Authenticate
) : HandlerFilterFunction<ServerResponse, ServerResponse> {
    override fun filter(
        request: ServerRequest,
        next: HandlerFunction<ServerResponse>
    ): ServerResponse =
        request.bearerToken()
            .flatMap { authenticate(it) }
            .mapBoth(
                success = {
                    val newRequest = ServerRequest.from(request)
                        .attribute(RequestAttributeKey.USER_ID, it.userId)
                        .build()
                    next.handle(newRequest)
                },
                failure = { WebErrorHandler.createErrorResponse(it) }
            )

    private fun ServerRequest.bearerToken(): Result<String, WebErrors> {
        return this.headers().header("Authorization")
            .firstOrNull()
            ?.takeIf { it.startsWith("Bearer ", ignoreCase = true) }
            ?.substring(7)
            ?.let { Ok(it) }
            ?: Err(
                WebErrors(
                    specErrors = listOf(WebSpecErrorType.NOT_AUTHORIZED)
                )
            )
    }

    private fun authenticate(token: String): Result<Authenticate.Output, WebErrors> =
        authenticate.authenticate(token)
            .mapError { WebErrors.fromUsecaseError(it) }
}
