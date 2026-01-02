package jp.glory.practice.boot.app.base.common.usecase.exception

import jp.glory.practice.boot.app.base.command.domain.exception.DomainSpecErrorType

enum class UsecaseSpecErrorType {
    LOGIN_IS_FAIL,
    NOT_AUTHORIZED,
    USER_ID_ALREADY_EXIST;

    companion object {
        fun fromDomainError(error: DomainSpecErrorType) =
            when (error) {
                DomainSpecErrorType.AUTHENTICATED_IS_FAIL -> LOGIN_IS_FAIL
                DomainSpecErrorType.USER_ID_ALREADY_EXIST -> USER_ID_ALREADY_EXIST
            }
    }
}
