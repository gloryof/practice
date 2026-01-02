package jp.glory.practice.boot.app.auth.data

import java.time.OffsetDateTime

data class TokenRecord(
    val userId: String,
    val token: String,
    val expiredAt: OffsetDateTime
)
