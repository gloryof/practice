package jp.glory.practice.boot.app.base.command.domain.validater

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemErrorType
import java.time.LocalDate
import kotlin.reflect.KClass


class LocalDateValidator(
    private val name: String,
    private val value: LocalDate,
    private val errors: MutableList<DomainItemErrorType> = mutableListOf()
) {

    constructor(
        classValue: KClass<*>,
        value: LocalDate
    ) : this(
        name = classValue.simpleName ?: throw IllegalStateException("Can not get class name"),
        value = value,
    )

    fun <T> parse(fn: (LocalDate) -> T): Result<T, DomainItemError> =
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

    fun validateIsBeforeOrEquals(compareAt: LocalDate) {
        if (value.isAfter(compareAt)) {
            errors.add(DomainItemErrorType.DATE_IS_AFTER)
        }
    }
}
