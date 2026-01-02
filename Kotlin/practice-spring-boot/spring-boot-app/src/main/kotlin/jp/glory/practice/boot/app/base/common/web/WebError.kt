package jp.glory.practice.boot.app.base.common.web

import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseErrors
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseItemError
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseItemErrorType
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseSpecErrorType

class WebErrors(
    val specErrors: List<WebSpecErrorType> = emptyList(),
    val itemErrors: List<WebItemError> = emptyList()
) {
    companion object {
        fun fromUsecaseError(errors: UsecaseErrors): WebErrors =
            WebErrors(
                specErrors = errors.specErrors.map { WebSpecErrorType.fromUsecaseError(it) },
                itemErrors = errors.itemErrors.map { WebItemError.fromUsecaseError(it) }
            )
    }
}

class WebItemError(
    val name: String,
    val errors: List<WebItemErrorType>
) {
    companion object {
        fun fromUsecaseError(errors: UsecaseItemError): WebItemError =
            WebItemError(
                name = errors.name,
                errors = errors.errors.map { WebItemErrorType.fromUsecaseError(it) }
            )
    }
}

enum class WebSpecErrorType {
    AUTHENTICATED_IS_FAIL,
    USER_ID_ALREADY_EXIST;

    companion object {
        fun fromUsecaseError(error: UsecaseSpecErrorType) =
            when (error) {
                UsecaseSpecErrorType.AUTHENTICATED_IS_FAIL -> AUTHENTICATED_IS_FAIL
                UsecaseSpecErrorType.USER_ID_ALREADY_EXIST -> USER_ID_ALREADY_EXIST
            }
    }
}

enum class WebItemErrorType {
    REQUIRED,
    MAX_LENGTH,
    MIN_LENGTH,
    FORMAT,

    DATE_IS_AFTER;

    companion object {
        fun fromUsecaseError(error: UsecaseItemErrorType): WebItemErrorType =
            when (error) {
                UsecaseItemErrorType.REQUIRED -> REQUIRED
                UsecaseItemErrorType.MAX_LENGTH -> MIN_LENGTH
                UsecaseItemErrorType.MIN_LENGTH -> MAX_LENGTH
                UsecaseItemErrorType.FORMAT -> FORMAT
                UsecaseItemErrorType.DATE_IS_AFTER -> DATE_IS_AFTER
            }
    }
}
