package jp.glory.practice.boot.app.user.command.domain.model

import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.domain.validater.StringValidator

@JvmInline
value class Password private constructor(val value: String) {
    companion object {
        const val MAX_LENGTH = 100
        const val MIN_LENGTH = 16

        private val VALID_CHAR = Regex("^[!-~¥]+$").toPattern()
        fun of(value: String): Result<Password, DomainErrors> =
            StringValidator(Password::class, value)
                .apply {
                    validateRequired()
                    validateMaxLength(MAX_LENGTH)
                    validatePattern(VALID_CHAR)
                }
                .run { parse { Password(it) } }
    }
}
