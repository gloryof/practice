package jp.glory.practice.boot.app.user.command.domain.model

import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.domain.validater.StringValidator

@JvmInline
value class UserName private constructor(val value: String) {
    companion object {
        const val MAX_LENGTH = 100
        fun of(value: String): Result<UserName, DomainErrors> =
            StringValidator(UserName::class, value)
                .apply {
                    validateRequired()
                    validateMaxLength(MAX_LENGTH)
                }
                .run { parse { UserName(it) } }
    }
}
