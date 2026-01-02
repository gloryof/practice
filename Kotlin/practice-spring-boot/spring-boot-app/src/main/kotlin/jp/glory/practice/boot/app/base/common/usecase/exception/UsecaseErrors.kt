package jp.glory.practice.boot.app.base.common.usecase.exception

import jp.glory.practice.boot.app.base.command.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemError

class UsecaseErrors(
    val specErrors: List<UsecaseSpecErrorType> = emptyList(),
    val itemErrors: List<UsecaseItemError> = emptyList()
) {
    companion object {
        fun fromDomainError(errors: DomainErrors): UsecaseErrors =
            UsecaseErrors(
                specErrors = errors.specErrors.map { UsecaseSpecErrorType.fromDomainError(it) },
                itemErrors = errors.itemErrors.map { UsecaseItemError.Companion.fromDomainError(it) }
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
