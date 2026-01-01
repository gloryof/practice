package jp.glory.practice.boot.app.user.command.domain.model

import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.domain.validater.StringValidator

@JvmInline
value class UserName private constructor(val value: String) {
    companion object {
        const val MAX_LENGTH = 100
        fun of(value: String): Result<UserName, DomainItemError> =
            StringValidator(UserName::class, value)
                .configure()
                .run { parse { UserName(it) } }

        fun validate(name: String, value: String): Result<Unit, DomainItemError> =
            StringValidator(name, value)
                .configure()
                .validate()

        private fun StringValidator.configure(): StringValidator =
            this.also {
                validateRequired()
                validateMaxLength(MAX_LENGTH)
            }
    }
}
