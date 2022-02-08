package jp.glory.ci.cd.practice.app.base.web

open class WebException(
    message: String?,
    cause: Throwable? = null
) : RuntimeException(message, cause)

class AuthenticationException() : WebException(
    message = "Authentication is fail"
)