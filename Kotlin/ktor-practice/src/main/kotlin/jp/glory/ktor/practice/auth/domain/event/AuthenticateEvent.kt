package jp.glory.ktor.practice.auth.domain.event

data class UserAuthenticateEvent(
    val id: InputUserId,
    val password: InputPassword
)
data class TokenAuthenticateEvent(
    val tokenValue: InputToken
)

@JvmInline
value class InputUserId(val value: String)

@JvmInline
value class InputPassword(val value: String)

@JvmInline
value class InputToken(val value: String)
