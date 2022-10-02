package jp.glory.app.coverage.practice.base.adaptor.web.error

data class ErrorResponse(
    val message: String,
    val details: List<String>
)