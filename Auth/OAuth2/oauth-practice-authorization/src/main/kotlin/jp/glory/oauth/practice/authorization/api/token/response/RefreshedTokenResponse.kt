package jp.glory.oauth.practice.authorization.api.token.response

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshedTokenResponse(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("scope") val scope: List<String>,
)