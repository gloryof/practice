package jp.glory.practice.boot.app.base.common.web.request

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.common.web.exception.WebErrors
import jp.glory.practice.boot.app.base.common.web.exception.WebSpecErrorType
import org.springframework.web.servlet.function.ServerRequest

object ServerRequestUtil {
    fun getLoginUserId(request: ServerRequest): Result<String, WebErrors> {
        val result = request.attribute(RequestAttributeKey.USER_ID)

        if (result.isEmpty) {
            return Err(
                WebErrors(
                    specErrors = listOf(WebSpecErrorType.UNAUTHORIZED)
                )
            )
        }

        return Ok(result.get() as String)
    }
}
