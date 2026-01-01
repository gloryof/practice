package jp.glory.practice.boot.app.user.command.domain.model

import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.domain.validater.LocalDateValidator
import java.time.LocalDate

@JvmInline
value class Birthday private constructor(val value: LocalDate) {
    companion object {
        fun of(value: LocalDate, today: LocalDate): Result<Birthday, DomainItemError> =
            LocalDateValidator(Birthday::class, value)
                .configure(today)
                .run { parse { Birthday(it) } }

        fun validate(name: String, value: LocalDate, today: LocalDate): Result<Unit, DomainItemError> =
            LocalDateValidator(name, value)
                .configure(today)
                .validate()

        private fun LocalDateValidator.configure(today: LocalDate): LocalDateValidator =
            this.apply {
                validateIsBeforeOrEquals(today)
            }
    }
}
