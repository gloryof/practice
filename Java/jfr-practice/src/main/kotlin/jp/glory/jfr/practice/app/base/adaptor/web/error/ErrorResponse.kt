package jp.glory.jfr.practice.app.base.adaptor.web.error

data class ErrorResponse(
    val message: String
)

data class ValidationErrorResponse(
    val message: String,
    val details: List<String>
)
