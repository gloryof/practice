package jp.glory.practice.boot.app.base.domain.validater

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.domain.exception.DomainError
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import java.time.LocalDate
import kotlin.reflect.KClass


class LocalDateValidator(
    private val classValue: KClass<*>,
    private val value: LocalDate,
    private val errors: MutableList<DomainError> = mutableListOf()
) {
    fun <T> parse(fn: (LocalDate) -> T): Result<T, DomainErrors> {
        if (errors.isNotEmpty()) {
            return Err(createErrors())
        }
        return Ok(fn(value))
    }

    private fun createErrors(): DomainErrors {
        val name = classValue.simpleName ?: throw IllegalStateException("Can not get class name")

        return DomainErrors(name, errors)
    }

    fun validateIsBeforeOrEquals(compareAt: LocalDate) {
        if (value.isAfter(compareAt)) {
            errors.add(DomainError.DATE_IS_AFTER)
        }
    }
}
