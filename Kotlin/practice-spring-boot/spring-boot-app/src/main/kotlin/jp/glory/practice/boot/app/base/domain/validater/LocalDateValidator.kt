package jp.glory.practice.boot.app.base.domain.validater

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.domain.exception.DomainItemErrorType
import java.time.LocalDate
import kotlin.reflect.KClass


class LocalDateValidator(
    private val classValue: KClass<*>,
    private val value: LocalDate,
    private val errors: MutableList<DomainItemErrorType> = mutableListOf()
) {
    fun <T> parse(fn: (LocalDate) -> T): Result<T, DomainItemError> {
        if (errors.isNotEmpty()) {
            return Err(createErrors())
        }
        return Ok(fn(value))
    }

    private fun createErrors(): DomainItemError {
        val name = classValue.simpleName ?: throw IllegalStateException("Can not get class name")

        return DomainItemError(name, errors)
    }

    fun validateIsBeforeOrEquals(compareAt: LocalDate) {
        if (value.isAfter(compareAt)) {
            errors.add(DomainItemErrorType.DATE_IS_AFTER)
        }
    }
}
