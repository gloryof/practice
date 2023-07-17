package jp.glory.ktor.practice.base.adaptor.web

import io.ktor.http.HttpStatusCode

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
    ERD400002("Authentication is failed"),
    ERD401001("Token is not authorized"),
    ERD404001("Request URL is not found"),
    ERD404002("%s is not found(id: %s)"),
    ERD500001("Unexpected error is occurred")
}

sealed class WebErrorDetail{
    abstract fun getHttpStatus(): HttpStatusCode

    protected abstract fun getErrorCode(): WebErrorCode
    fun getErrorCodeValue(): String = getErrorCode().name
    fun getErrorMessage(): String = getErrorCode().message
    protected abstract fun getErrorDetailCode(): WebErrorDetailCode
    fun getErrorDetailCodeValue(): String = getErrorDetailCode().name
    open fun getErrorDetailMessage(): String =  getErrorDetailCode().message
    open fun getErrorDetailMessageArgument(): Array<String> = emptyArray()
}

class WebGenericErrorDetail private constructor(
    private val httpStatusCode: HttpStatusCode,
    private val errorCode: WebErrorCode,
    private val errorDetailCode: WebErrorDetailCode
) : WebErrorDetail() {
    companion object {
        fun createGenericErrorDetail(
            httpStatusCode: HttpStatusCode
        ): WebGenericErrorDetail {
            return WebGenericErrorDetail(
                httpStatusCode = httpStatusCode,
                errorCode = toWebErrorCode(httpStatusCode),
                errorDetailCode = toWebDetailErrorCode(httpStatusCode)
            )
        }

        private fun toWebErrorCode(status: HttpStatusCode): WebErrorCode =
            when(status) {
                HttpStatusCode.BadRequest -> WebErrorCode.ERR400
                HttpStatusCode.Unauthorized -> WebErrorCode.ERR401
                HttpStatusCode.NotFound -> WebErrorCode.ERR404
                else -> WebErrorCode.ERR500
            }

        private fun toWebDetailErrorCode(
            httpStatusCode: HttpStatusCode
        ): WebErrorDetailCode =
            if (httpStatusCode.value in 400..499) {
                convertFromB4xxError(httpStatusCode)
            } else {
                WebErrorDetailCode.ERD500001
            }

        private fun convertFromB4xxError(
            httpStatusCode: HttpStatusCode
        ): WebErrorDetailCode =
            when(httpStatusCode.value) {
                401 -> WebErrorDetailCode.ERD401001
                403 -> WebErrorDetailCode.ERD404001
                404 -> WebErrorDetailCode.ERD404001
                405 -> WebErrorDetailCode.ERD404001
                406 -> WebErrorDetailCode.ERD404001
                else -> WebErrorDetailCode.ERD400001
            }
    }
    override fun getHttpStatus(): HttpStatusCode = httpStatusCode

    override fun getErrorCode(): WebErrorCode = errorCode

    override fun getErrorDetailCode(): WebErrorDetailCode = errorDetailCode
}

class WebTargetNotFoundErrorDetail(
    private val resourceName: String,
    private val idValue: String
): WebErrorDetail() {
    override fun getHttpStatus(): HttpStatusCode = HttpStatusCode.NotFound

    override fun getErrorCode(): WebErrorCode = WebErrorCode.ERR404

    override fun getErrorDetailCode(): WebErrorDetailCode = WebErrorDetailCode.ERD404002

    override fun getErrorDetailMessage(): String =
        getErrorDetailCode().message.format(resourceName, idValue)

    override fun getErrorDetailMessageArgument(): Array<String> =
        arrayOf(resourceName, idValue)
}


object WebAuthenticationFailedError: WebErrorDetail() {
    override fun getHttpStatus(): HttpStatusCode = HttpStatusCode.BadRequest

    override fun getErrorCode(): WebErrorCode = WebErrorCode.ERR400

    override fun getErrorDetailCode(): WebErrorDetailCode = WebErrorDetailCode.ERD400002

}

