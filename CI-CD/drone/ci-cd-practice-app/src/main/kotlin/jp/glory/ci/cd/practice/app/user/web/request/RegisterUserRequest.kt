package jp.glory.ci.cd.practice.app.user.web.request

data class RegisterUserRequest(
    val givenName: String,
    val familyName: String,
    val password: String
)