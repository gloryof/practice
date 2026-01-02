package jp.glory.practice.boot.app.auth.data

import java.time.OffsetTime

data class TokenRecord(
    val userId: String,
    val token: String,
    val expiredAt: OffsetTime
)
