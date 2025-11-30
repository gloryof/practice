package jp.glory.practice.boot.app.user.domain.command.model

import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.domain.validater.StringValidator

@JvmInline
value class LoginId private constructor(val value: String) {
    companion object {
        const val MAX_LENGTH = 100

        // 半角文字は-._のみ許可
        private val VALID_CHAR = Regex("^[a-zA-Z0-9.\\-_]+$").toPattern()
        fun of(value: String): Result<LoginId, DomainErrors> =
            StringValidator(LoginId::class, value)
                .apply {
                    validateRequired()
                    validateMaxLength(MAX_LENGTH)
                    validatePattern(VALID_CHAR)
                }
                .run { parse { LoginId(it) } }
    }
}
