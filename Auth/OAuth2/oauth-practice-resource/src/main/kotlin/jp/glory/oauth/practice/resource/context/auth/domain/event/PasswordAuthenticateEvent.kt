package jp.glory.oauth.practice.resource.context.auth.domain.event

data class PasswordAuthenticateEvent(
    val loginId: String,
    val plainPassword: String
)