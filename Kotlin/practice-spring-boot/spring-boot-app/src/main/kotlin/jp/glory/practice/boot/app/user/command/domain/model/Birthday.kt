package jp.glory.practice.boot.app.user.command.domain.model

import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.domain.validater.LocalDateValidator
import java.time.LocalDate

@JvmInline
value class Birthday(val value: LocalDate) {
    companion object {
        fun of(value: LocalDate, today: LocalDate): Result<Birthday, DomainErrors> =
            LocalDateValidator(Birthday::class, value)
                .apply {
                    validateIsBeforeOrEquals(today)
                }
                .run { parse { Birthday(it) } }
    }
}
