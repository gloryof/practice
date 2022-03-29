package jp.glory.ci.cd.practice.app.user.web.request

data class UpdateUserRequest(
    val givenName: String,
    val familyName: String,
)