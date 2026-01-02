package jp.glory.practice.boot.app.auth.command.domain.model

import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.domain.validater.StringValidator

@JvmInline
value class Password private constructor(val value: String) {
    companion object {
        const val MAX_LENGTH = 100
        const val MIN_LENGTH = 16

        private val VALID_CHAR = Regex("^[!-~¥]+$").toPattern()
        fun of(value: String): Result<Password, DomainItemError> =
            StringValidator(Password::class, value)
                .configure()
                .run { parse { Password(it) } }

        fun validate(name: String, value: String): Result<Unit, DomainItemError> =
            StringValidator(name, value)
                .configure()
                .validate()

        private fun StringValidator.configure(): StringValidator =
            this.also {
                validateRequired()
                validateMaxLength(MAX_LENGTH)
                validateMinLength(MIN_LENGTH)
                validatePattern(VALID_CHAR)
            }
    }
}
