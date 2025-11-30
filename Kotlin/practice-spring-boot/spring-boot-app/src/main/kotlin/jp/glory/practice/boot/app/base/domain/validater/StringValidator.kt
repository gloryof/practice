package jp.glory.practice.boot.app.base.domain.validater

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainError
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import java.util.regex.Pattern
import kotlin.reflect.KClass


class StringValidator(
    private val classValue: KClass<*>,
    private val value: String,
    private val errors: MutableList<DomainError> = mutableListOf()
) {
    fun <T> parse(fn: (String) -> T): Result<T, DomainErrors> {
        if (errors.isNotEmpty()) {
            return Err(createErrors())
        }
        return Ok(fn(value))
    }

    private fun createErrors(): DomainErrors {
        val name = classValue.simpleName ?: throw IllegalStateException("Can not get class name")

        return DomainErrors(name, errors)
    }

    fun validateRequired() {
        if (value.isBlank()) {
            errors.add(DomainError.REQUIRED)
        }
    }

    fun validateMaxLength(length: Int) {
        if (value.count() > length) {
            errors.add(DomainError.MAX_LENGTH)
        }
    }

    fun validateMinLength(length: Int) {
        if (value.count() < length) {
            errors.add(DomainError.MIN_LENGTH)
        }
    }

    fun validatePattern(pattern: Pattern) {
        if (!pattern.matcher(value).matches()) {
            errors.add(DomainError.FORMAT)
        }
    }
}
