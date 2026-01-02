package jp.glory.practice.boot.app.base.command.domain.validater

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemErrorType
import java.util.regex.Pattern
import kotlin.reflect.KClass


class StringValidator(
    private val name: String,
    private val value: String,
    private val errors: MutableList<DomainItemErrorType> = mutableListOf()
) {
    constructor(
        classValue: KClass<*>,
        value: String
    ) : this(
        name = classValue.simpleName ?: throw IllegalStateException("Can not get class name"),
        value = value,
    )

    fun <T> parse(fn: (String) -> T): Result<T, DomainItemError> =
        validate()
            .map { fn(value) }

    fun validate(): Result<Unit, DomainItemError> {
        if (errors.isNotEmpty()) {
            return Err(createErrors())
        }
        return Ok(Unit)
    }

    private fun createErrors(): DomainItemError {
        return DomainItemError(name, errors)
    }

    fun validateRequired() {
        if (value.isBlank()) {
            errors.add(DomainItemErrorType.REQUIRED)
        }
    }

    fun validateMaxLength(length: Int) {
        if (value.isNotEmpty()) {
            if (value.count() > length) {
                errors.add(DomainItemErrorType.MAX_LENGTH)
            }
        }
    }

    fun validateMinLength(length: Int) {
        if (value.isNotEmpty()) {
            if (value.count() < length) {
                errors.add(DomainItemErrorType.MIN_LENGTH)
            }
        }
    }

    fun validatePattern(pattern: Pattern) {
        if (value.isNotEmpty()) {
            if (!pattern.matcher(value).matches()) {
                errors.add(DomainItemErrorType.FORMAT)
            }
        }
    }
}
