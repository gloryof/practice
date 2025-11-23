package jp.glory.practice.boot.app.base.domain.exception

class DomainErrors(
    val name: String,
    val errors: List<DomainError>
)

enum class DomainError {
    REQUIRED,
    MAX_LENGTH
}
