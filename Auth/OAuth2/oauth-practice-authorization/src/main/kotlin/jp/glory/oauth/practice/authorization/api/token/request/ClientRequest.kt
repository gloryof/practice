package jp.glory.oauth.practice.authorization.api.token.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ClientRequest(
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("scope") val scope: String,
)