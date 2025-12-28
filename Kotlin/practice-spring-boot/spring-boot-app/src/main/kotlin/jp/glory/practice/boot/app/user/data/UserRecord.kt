package jp.glory.practice.boot.app.user.data

import java.time.LocalDate

data class UserRecord(
    val userId: String,
    val loginId: String,
    val userName: String,
    val birthday: LocalDate,
)
