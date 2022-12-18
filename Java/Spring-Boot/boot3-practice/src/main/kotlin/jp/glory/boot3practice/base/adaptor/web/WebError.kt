package jp.glory.boot3practice.base.adaptor.web

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class WebErrorCode(
    val message: String,
) {
    ERR400("Invalid request"),
    ERR401("Request is not authorized"),
    ERR404("Target resource is not found"),
    ERR500("Unknown error is occurred")
}
enum class WebErrorDetailCode(
    val message: String,
) {
    ERD400001("Request is invalid"),
    ERD401001("Token is not authorized"),
    ERD404001("Request URL is not found"),
    ERD404002("%s is not found(id: %s)"),
    ERD500001("Unexpected error is occurred")
}

sealed class WebErrorDetail {
    abstract fun getHttpStatus(): HttpStatus

    protected abstract fun getErrorCode(): WebErrorCode
    fun getErrorCodeValue(): String = getErrorCode().name
    fun getErrorMessage(): String = getErrorCode().message
    protected abstract fun getErrorDetailCode(): WebErrorDetailCode
    fun getErrorDetailCodeValue(): String = getErrorDetailCode().name
    open fun getErrorDetailMessage(): String =  getErrorDetailCode().message
    open fun getErrorDetailMessageArgument(): Array<String> = emptyArray()
}

class WebGenericErrorDetail private constructor(
    private val httpStatus: HttpStatus,
    private val errorCode: WebErrorCode,
    private val errorDetailCode: WebErrorDetailCode
) : WebErrorDetail() {
    companion object {
        fun createGenericErrorDetail(
            httpStatusCode: HttpStatusCode
        ): WebGenericErrorDetail {
            val httpStatus = toHttpStatus(httpStatusCode)
            return WebGenericErrorDetail(
                httpStatus = httpStatus,
                errorCode = toWebErrorCode(httpStatus),
                errorDetailCode = toWebDetailErrorCode(httpStatusCode)
            )
        }
        private fun toHttpStatus(code: HttpStatusCode): HttpStatus =
            if (code.is4xxClientError) {
                when(code.value()) {
                    401 -> HttpStatus.UNAUTHORIZED
                    403 -> HttpStatus.NOT_FOUND
                    else -> HttpStatus.BAD_REQUEST
                }
            } else {
                HttpStatus.valueOf(code.value())
            }

        private fun toWebErrorCode(status: HttpStatus): WebErrorCode =
            when(status) {
                HttpStatus.BAD_REQUEST -> WebErrorCode.ERR400
                HttpStatus.UNAUTHORIZED -> WebErrorCode.ERR401
                HttpStatus.NOT_FOUND -> WebErrorCode.ERR404
                else -> WebErrorCode.ERR500
            }

        private fun toWebDetailErrorCode(
            httpStatusCode: HttpStatusCode
        ): WebErrorDetailCode =
            if (httpStatusCode.is4xxClientError) {
                convertFromB4xxError(httpStatusCode)
            } else {
                WebErrorDetailCode.ERD500001
            }

        private fun convertFromB4xxError(
            httpStatusCode: HttpStatusCode
        ): WebErrorDetailCode =
            when(httpStatusCode.value()) {
                401 -> WebErrorDetailCode.ERD401001
                403 -> WebErrorDetailCode.ERD404001
                404 -> WebErrorDetailCode.ERD404001
                405 -> WebErrorDetailCode.ERD404001
                406 -> WebErrorDetailCode.ERD404001
                else -> WebErrorDetailCode.ERD400001
            }
    }
    override fun getHttpStatus(): HttpStatus = httpStatus

    override fun getErrorCode(): WebErrorCode = errorCode

    override fun getErrorDetailCode(): WebErrorDetailCode = errorDetailCode

}

class WebTargetNotFoundErrorDetail(
    private val resourceName: String,
    private val idValue: String
): WebErrorDetail() {
    override fun getHttpStatus(): HttpStatus = HttpStatus.NOT_FOUND

    override fun getErrorCode(): WebErrorCode = WebErrorCode.ERR404

    override fun getErrorDetailCode(): WebErrorDetailCode = WebErrorDetailCode.ERD404002

    override fun getErrorDetailMessage(): String =
        getErrorDetailCode().message.format(resourceName, idValue)

    override fun getErrorDetailMessageArgument(): Array<String> =
        arrayOf(resourceName, idValue)
}

