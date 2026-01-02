package jp.glory.practice.boot.app.base.common.usecase.exception

import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemErrorType

enum class UsecaseItemErrorType {
    REQUIRED,
    MAX_LENGTH,
    MIN_LENGTH,
    FORMAT,

    DATE_IS_AFTER;

    companion object {
        fun fromDomainError(error: DomainItemErrorType): UsecaseItemErrorType =
            when (error) {
                DomainItemErrorType.REQUIRED -> REQUIRED
                DomainItemErrorType.MAX_LENGTH -> MIN_LENGTH
                DomainItemErrorType.MIN_LENGTH -> MAX_LENGTH
                DomainItemErrorType.FORMAT -> FORMAT
                DomainItemErrorType.DATE_IS_AFTER -> DATE_IS_AFTER
            }
    }
}
