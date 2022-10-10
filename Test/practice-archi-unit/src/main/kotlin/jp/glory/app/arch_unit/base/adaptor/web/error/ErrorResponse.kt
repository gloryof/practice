package jp.glory.app.arch_unit.base.adaptor.web.error

data class ErrorResponse(
    val message: String,
    val details: List<String>
)