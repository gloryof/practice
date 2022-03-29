package jp.glory.ci.cd.practice.app.user.web.response

data class UserResponse(
    val userId: String,
    val givenName: String,
    val familyName: String
)