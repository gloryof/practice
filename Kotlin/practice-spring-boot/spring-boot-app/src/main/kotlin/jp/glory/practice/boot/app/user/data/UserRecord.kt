package jp.glory.practice.boot.app.user.data

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import java.time.LocalDate

@KomapperEntity
@KomapperTable("users")
data class UserRecord(
    @KomapperId
    val userId: String,
    val userName: String,
    val birthday: LocalDate
)
