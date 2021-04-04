package jp.glory.oauth.practice.authorization.spec.access_token

import jp.glory.oauth.practice.authorization.spec.Scope
import java.time.LocalDateTime

data class AccessTokenRegisterResult(
    val token: AccessToken,
    val state: String,
)