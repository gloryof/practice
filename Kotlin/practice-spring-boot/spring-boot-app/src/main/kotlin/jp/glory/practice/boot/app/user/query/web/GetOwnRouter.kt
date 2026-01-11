package jp.glory.practice.boot.app.user.query.web

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import jp.glory.practice.boot.app.base.common.web.exception.WebErrorHandler
import jp.glory.practice.boot.app.base.common.web.exception.WebErrors
import jp.glory.practice.boot.app.base.common.web.request.ServerRequestUtil
import jp.glory.practice.boot.app.user.query.usecase.GetUser
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.time.LocalDate

class GetOwnRouter(
    private val usecase: GetUser
) {

    fun getOwn(request: ServerRequest): ServerResponse =
        ServerRequestUtil.getLoginUserId(request)
            .flatMap { getUser(it) }
            .mapBoth(
                success = { createSuccessResponse(it) },
                failure = { WebErrorHandler.createErrorResponse(it) }
            )

    private fun getUser(string: String): Result<GetUser.Output, WebErrors> =
        usecase.getById(string)
            .mapError { WebErrors.fromUsecaseError(it) }

    private fun createSuccessResponse(user: GetUser.Output): ServerResponse =
        ServerResponse.ok()
            .body(
                Response(
                    userId = user.userId,
                    userName = user.userName,
                    birthday = user.birthday
                )
            )

    class Response(
        val userId: String,
        val userName: String,
        val birthday: LocalDate
    )
}
