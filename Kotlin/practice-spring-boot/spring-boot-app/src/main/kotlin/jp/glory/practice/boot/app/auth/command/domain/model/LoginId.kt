package jp.glory.practice.boot.app.auth.command.domain.model

import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.command.domain.validater.StringValidator

@JvmInline
value class LoginId private constructor(val value: String) {
    companion object {
        const val MAX_LENGTH = 100

        // 半角文字は-._のみ許可
        private val VALID_CHAR = Regex("^[a-zA-Z0-9.\\-_]+$").toPattern()
        fun of(value: String): Result<LoginId, DomainItemError> =
            StringValidator(LoginId::class, value)
                .configure()
                .run { parse { LoginId(it) } }

        fun validate(name: String, value: String): Result<Unit, DomainItemError> =
            StringValidator(name, value)
                .configure()
                .validate()

        private fun StringValidator.configure(): StringValidator =
            this.also {
                validateRequired()
                validateMaxLength(MAX_LENGTH)
                validatePattern(VALID_CHAR)
            }
    }
}
