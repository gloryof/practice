package jp.glory.practice.boot.app.base.command.domain.exception

class DomainErrors(
    val specErrors: List<DomainSpecErrorType> = emptyList(),
    val itemErrors: List<DomainItemError> = emptyList()
)

class DomainItemError(
    val name: String,
    val errors: List<DomainItemErrorType>
)

enum class DomainSpecErrorType {
    AUTHENTICATED_IS_FAIL,
    USER_ID_ALREADY_EXIST
}

enum class DomainItemErrorType {
    REQUIRED,
    MAX_LENGTH,
    MIN_LENGTH,
    FORMAT,

    DATE_IS_AFTER
}
