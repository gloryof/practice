package jp.glory.ci.cd.practice.app.auth.domain.model

class RegisteredToken(
    val credentialUserId: CredentialUserId,
    val tokenValue: TokenValue
)