package jp.glory.practicegraphql.app.base.adaptor.web.error

import graphql.GraphQLError
import jp.glory.practicegraphql.app.base.usecase.UseCaseError
import jp.glory.practicegraphql.app.base.usecase.UseCaseUnknownError

sealed class WebError {
    fun createException(): WebException = WebException(this)
}

data class WebUnknownError(
    val message: String,
    val cause: Throwable
) : WebError() {
    constructor(error: UseCaseUnknownError): this(
        message = error.message,
        cause = error.cause
    )
}

data class NotFoundError(
    val message: String
) : WebError()

fun toWebError(useCaseError: UseCaseError): WebError =
    when(useCaseError) {
        is UseCaseUnknownError -> WebUnknownError(useCaseError)
    }