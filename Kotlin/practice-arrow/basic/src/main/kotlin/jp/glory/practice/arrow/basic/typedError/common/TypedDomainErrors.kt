package jp.glory.practice.arrow.basic.typedError.common

sealed class TypedDomainError(
    val message: String
)

class ValidatedError : TypedDomainError(
    message = "Invalid failed"
) {
    private val errors = mutableMapOf<InvalidResource, MutableList<InvalidType>>()

    companion object {
        fun merge(errors: List<ValidatedError>): ValidatedError {
            val merged = ValidatedError()

            errors.forEach { merged.addAll(it) }

            return merged
        }
    }

    fun getErrors(): List<ValidateErrorDetail> = errors
        .flatMap { entry ->
            entry.value.map {
                ValidateErrorDetail(
                    resource = entry.key,
                    type = it
                )
            }
        }

    fun addAll(other: ValidatedError) {
        other.errors
            .forEach { (resource, value) ->
                errors.computeIfAbsent(resource) {
                    mutableListOf()
                }
                    .let { it.addAll(value) }
            }
    }

    fun addRequiredError(resource: InvalidResource): ValidatedError {
        addError(resource, InvalidType.Required)
        return this
    }

    fun addMaxLengthOverError(resource: InvalidResource): ValidatedError {
        addError(resource, InvalidType.MaxLengthOver)
        return this
    }

    fun addFutureBirthday(resource: InvalidResource): ValidatedError {
        addError(resource, InvalidType.FutureBirthday)
        return this
    }

    private fun addError(
        resource: InvalidResource,
        type: InvalidType
    ) {
        errors.computeIfAbsent(resource) {
            mutableListOf()
        }
            .let { it.add(type) }
    }

}


data object UserNotFoundError : TypedDomainError(
    message = "User Not found"
)

data object SaveIsFailedError : TypedDomainError(
    message = "Save is failed"
)

class ValidateErrorDetail(
    val resource: InvalidResource,
    val type: InvalidType
) {
}

enum class InvalidResource {
    UserId,
    UserName,
    Birthday
}
enum class InvalidType {
    Required,
    MaxLengthOver,
    FutureBirthday
}