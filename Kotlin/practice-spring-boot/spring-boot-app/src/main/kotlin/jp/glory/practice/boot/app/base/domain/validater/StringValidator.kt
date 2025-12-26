package jp.glory.practice.boot.app.base.domain.validater

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.domain.exception.DomainItemErrorType
import java.util.regex.Pattern
import kotlin.reflect.KClass


class StringValidator(
    private val classValue: KClass<*>,
    private val value: String,
    private val errors: MutableList<DomainItemErrorType> = mutableListOf()
) {
    fun <T> parse(fn: (String) -> T): Result<T, DomainItemError> {
        if (errors.isNotEmpty()) {
            return Err(createErrors())
        }
        return Ok(fn(value))
    }

    private fun createErrors(): DomainItemError {
        val name = classValue.simpleName ?: throw IllegalStateException("Can not get class name")

        return DomainItemError(name, errors)
    }

    fun validateRequired() {
        if (value.isBlank()) {
            errors.add(DomainItemErrorType.REQUIRED)
        }
    }

    fun validateMaxLength(length: Int) {
        if (value.count() > length) {
            errors.add(DomainItemErrorType.MAX_LENGTH)
        }
    }

    fun validateMinLength(length: Int) {
        if (value.count() < length) {
            errors.add(DomainItemErrorType.MIN_LENGTH)
        }
    }

    fun validatePattern(pattern: Pattern) {
        if (!pattern.matcher(value).matches()) {
            errors.add(DomainItemErrorType.FORMAT)
        }
    }
}
