package jp.glory.app.open_telemetry.practice.base.adaptor.web.error

data class ErrorResponse(
    val message: String,
    val details: List<String>
)