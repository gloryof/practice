package jp.glory.practice.boot.app.base.Usecase.usecase.exception

import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.domain.exception.DomainItemErrorType
import jp.glory.practice.boot.app.base.domain.exception.DomainSpecErrorType

class UsecaseErrors(
    val specErrors: List<UsecaseSpecErrorType> = emptyList(),
    val itemErrors: List<UsecaseItemError> = emptyList()
) {
    companion object {
        fun fromDomainError(errors: DomainErrors): UsecaseErrors =
            UsecaseErrors(
                specErrors = errors.specErrors.map { UsecaseSpecErrorType.fromDomainError(it) },
                itemErrors = errors.itemErrors.map { UsecaseItemError.fromDomainError(it) }
            )
    }
}

class UsecaseItemError(
    val name: String,
    val errors: List<UsecaseItemErrorType>
) {
    companion object {
        fun fromDomainError(errors: DomainItemError): UsecaseItemError =
            UsecaseItemError(
                name = errors.name,
                errors = errors.errors.map { UsecaseItemErrorType.fromDomainError(it) }
            )
    }
}

enum class UsecaseSpecErrorType {
    AUTHENTICATED_IS_FAIL,
    USER_ID_ALREADY_EXIST;

    companion object {
        fun fromDomainError(error: DomainSpecErrorType) =
            when (error) {
                DomainSpecErrorType.AUTHENTICATED_IS_FAIL -> AUTHENTICATED_IS_FAIL
                DomainSpecErrorType.USER_ID_ALREADY_EXIST -> USER_ID_ALREADY_EXIST
            }
    }
}

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
