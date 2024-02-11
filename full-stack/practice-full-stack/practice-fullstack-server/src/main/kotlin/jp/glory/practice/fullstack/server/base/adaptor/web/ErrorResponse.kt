package jp.glory.practice.fullstack.server.base.adaptor.web

class ErrorResponse private constructor(
    val message: String,
    val type: ErrorType
) {
    companion object {

        fun createValidationError(message: String): ErrorResponse =
            ErrorResponse(
                type = ErrorType.InvalidRequest,
                message = message
            )

        fun createAuthorizationError(message: String): ErrorResponse =
            ErrorResponse(
                type = ErrorType.NotAuthorized,
                message = message
            )
        fun createNotFoundException(
            message: String
        ): ErrorResponse =
            ErrorResponse(
                message = message,
                type = ErrorType.NotFound
            )
        fun createInternalError(): ErrorResponse =
            ErrorResponse(
                message = "Internal error is occurred",
                type = ErrorType.InternalError
            )
    }
}

enum class ErrorType {
    NotAuthorized,
    InvalidRequest,
    NotFound,
    InternalError,
}