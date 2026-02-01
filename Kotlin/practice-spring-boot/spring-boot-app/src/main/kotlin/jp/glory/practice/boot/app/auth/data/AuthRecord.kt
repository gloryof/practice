package jp.glory.practice.boot.app.auth.data

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity
@KomapperTable("auths")
data class AuthRecord(
    @KomapperId
    val loginId: String,
    val userId: String,
    val password: String
)
