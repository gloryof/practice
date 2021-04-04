package jp.glory.oauth.practice.authorization.spec.auth_code

import jp.glory.oauth.practice.authorization.spec.Scope
import java.time.LocalDateTime

data class AuthCodeRegisterResult(
    val code: AuthCode,
    val state: String,
)