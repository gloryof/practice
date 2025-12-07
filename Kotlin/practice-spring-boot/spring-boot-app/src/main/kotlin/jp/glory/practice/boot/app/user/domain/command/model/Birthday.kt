package jp.glory.practice.boot.app.user.domain.command.model

import java.time.LocalDate

@JvmInline
value class Birthday(val value: LocalDate) {
    companion object {
        fun of(value: LocalDate): Birthday {
            TODO()
        }
    }
}
