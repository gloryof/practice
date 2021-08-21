package jp.glory.usecase

import com.github.michaelbull.result.*
import jp.glory.domain.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

class RegisterUserUseCase(
    private val repository: UserRepository
) {
    data class Input(
        val familyName: String,
        val givenName: String,
        val birthDay: String
    )

    @JvmInline
    value class RegisteredId(val value: String)

    fun register(input: Input): Result<RegisteredId, UseCaseError> =
        RegisterUserValidator
            .validate(input)
            .flatMap { generateId() }
            .map { id -> convertToUser(id, input) }
            .flatMap { save(it) }
            .mapBoth(
                success = { Ok(RegisteredId(it.value)) },
                failure = { Err(it) }
            )

    private fun generateId(): Result<UserId, UseCaseError> =
        repository.generateId()
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(convertToUseCaseError(it)) }
            )

    private fun save(user: User): Result<UserId, UseCaseError> =
        repository.save(user)
            .mapBoth(
                success = { Ok(user.id) },
                failure = { Err(convertToUseCaseError(it)) }
            )

    private fun convertToUseCaseError(domainError: DomainError): UseCaseError =
        when(domainError) {
            DomainError.Unknown -> UnknownError("Unknown error is occurred")
        }

    private fun convertToUser(
        id: UserId,
        input: Input
    ): User =
        User(
            id = id,
            name = Name(
                familyName = input.familyName,
                givenName = input.givenName
            ),
            birthDay = LocalDate.parse(input.birthDay)
        )
}

object RegisterUserValidator {
    private val datePattern = Regex("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")
    private val namePattern = Regex("[0-9a-zA-Z]+")
    private val dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT)
    fun validate(input: RegisterUserUseCase.Input): Result<Unit, ValidationErrors> {
        val errors = ValidationErrors()
        errors.add(validateFamilyName(input.familyName))
        errors.add(validateGivenName(input.givenName))
        errors.add(validateBirthDay(input.birthDay))

        if (errors.isError()) {
            return Err(errors)
        }

        return Ok(Unit)
    }
    private fun validateBirthDay(birthDay: String): ValidationError {
        val error = ValidationError("birthDay")

        if (birthDay.isEmpty()) {
            error.addError(
                ValidationErrorDetail(
                    type = ValidationErrorType.Required,
                )
            )
            return error
        }

        if (!datePattern.matches(birthDay)) {
            error.addError(
                ValidationErrorDetail(
                    type = ValidationErrorType.InvalidFormat,
                    attributes = mapOf(
                        "format" to "yyyy-MM-dd"
                    )
                )
            )
            return error
        }

        kotlin.runCatching {
            dateFormatter.parse(birthDay)
        }.onFailure {
            error.addError(
                ValidationErrorDetail(
                    type = ValidationErrorType.InvalidValue,
                )
            )
        }

        return error
    }
    private fun validateFamilyName(familyName: String): ValidationError {
        val error = ValidationError("familyName")
        if (familyName.isEmpty()) {
            error.addError(
                ValidationErrorDetail(
                    type = ValidationErrorType.Required,
                )
            )
            return error
        }

        if (!namePattern.matches(familyName)) {
            error.addError(
                ValidationErrorDetail(
                    type = ValidationErrorType.InvalidValue,
                )
            )
        }

        return error
    }

    private fun validateGivenName(givenName: String): ValidationError {
        val error = ValidationError("givenName")
        if (givenName.isEmpty()) {
            error.addError(
                ValidationErrorDetail(
                    type = ValidationErrorType.Required,
                )
            )
            return error
        }

        if (!namePattern.matches(givenName)) {
            error.addError(
                ValidationErrorDetail(
                    type = ValidationErrorType.InvalidValue,
                )
            )
        }

        return error
    }
}