package jp.glory.oauth.practice.authorization.api.token.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    val error: String,
    @JsonProperty("error_description") val errorDescription: String
)