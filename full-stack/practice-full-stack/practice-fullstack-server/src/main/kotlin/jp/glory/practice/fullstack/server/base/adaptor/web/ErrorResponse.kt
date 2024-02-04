package jp.glory.practice.fullstack.server.base.adaptor.web

class ErrorResponse private constructor(
    val type: ErrorType
) {
    companion object {

        fun createValidationError(): ErrorResponse =
            ErrorResponse(
                type = ErrorType.InvalidRequest
            )
        fun createUnauthorizedError(): ErrorResponse =
            ErrorResponse(
                type = ErrorType.Unauthorized
            )
        fun createInternalError(): ErrorResponse =
            ErrorResponse(
                type = ErrorType.InternalError
            )
    }
}

enum class ErrorType {
    InvalidRequest,
    Unauthorized,
    InternalError
}