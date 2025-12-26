package jp.glory.practice.boot.app.user.data.auth

data class AuthRecord(
    val loginId: String,
    val userId: String,
    val password: String
)
