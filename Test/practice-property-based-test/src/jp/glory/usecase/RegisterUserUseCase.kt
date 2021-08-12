package jp.glory.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

class RegisterUserUseCase {
    data class Input(
        val birthDay: String
    )
}

object RegisterUserValidator {
    private val pattern = Regex("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")
    private val dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT)
    fun validate(input: RegisterUserUseCase.Input): Result<Unit, ValidationErrors> {
        val errors = ValidationErrors()
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

        if (!pattern.matches(birthDay)) {
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
}