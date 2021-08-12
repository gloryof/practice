package jp.glory.usecase

sealed class UseCaseError

class ValidationErrors {
    private val errors = mutableListOf<ValidationError>()
    fun getErrors(): List<ValidationError> = errors.toList()
    fun add(error: ValidationError) {
        if (error.isError()) {
            errors.add(error)
        }
    }
    fun isError(): Boolean = errors.isNotEmpty()
}

class ValidationError(
    val field: String,
) : UseCaseError() {
    private val details = mutableListOf<ValidationErrorDetail>()
    fun getDetails(): List<ValidationErrorDetail> = details.toList()
    fun isError(): Boolean = details.isNotEmpty()
    fun addError(detail: ValidationErrorDetail) = details.add(detail)
}

class ValidationErrorDetail(
    val type: ValidationErrorType,
    val attributes: Map<String, String> = emptyMap()
) : UseCaseError()

enum class ValidationErrorType {
    Required,
    InvalidFormat,
    InvalidValue
}