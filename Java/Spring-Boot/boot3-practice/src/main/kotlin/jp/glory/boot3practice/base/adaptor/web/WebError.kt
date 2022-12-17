package jp.glory.boot3practice.base.adaptor.web

enum class WebErrorCode(
    val message: String,
) {
    ERR404("Target resource is not found"),
    ERR500("Unknown error is occurred")
}
enum class WebErrorDetailCode(
    val message: String,
) {
    ERD404001("%s is not found(id: %s)"),
    ERD500001("Unexpected error is occurred")
}

sealed class WebErrorDetail {
    abstract fun getCode(): WebErrorDetailCode
    abstract fun getMessage(): String
    open fun getMesssageArgument(): Array<String> = emptyArray()
}

class WebNotFoundErrorDetail(
    private val resourceName: String,
    private val idValue: String
): WebErrorDetail() {
    override fun getCode(): WebErrorDetailCode = WebErrorDetailCode.ERD404001

    override fun getMessage(): String =
        getCode().message.format(resourceName, idValue)

    override fun getMesssageArgument(): Array<String> =
        arrayOf(resourceName, idValue)
}


object WebUnknownErrorDetail: WebErrorDetail() {
    override fun getCode(): WebErrorDetailCode = WebErrorDetailCode.ERD500001

    override fun getMessage(): String = getCode().message

}

