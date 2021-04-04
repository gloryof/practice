package jp.glory.oauth.practice.authorization.api.token.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshRequest(
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("refresh_token") val refreshToken: String,
    @JsonProperty("scope") val scope: String,
)