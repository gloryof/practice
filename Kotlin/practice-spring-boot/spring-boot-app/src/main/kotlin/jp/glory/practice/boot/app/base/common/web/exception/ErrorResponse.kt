package jp.glory.practice.boot.app.base.common.web.exception

class ErrorResponse(
    val status: Int,
    val title: String,
    val errors: List<ErrorDetail>
)
