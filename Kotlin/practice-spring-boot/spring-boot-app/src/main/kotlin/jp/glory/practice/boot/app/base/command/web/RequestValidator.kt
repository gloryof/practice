package jp.glory.practice.boot.app.base.command.web

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import jp.glory.practice.boot.app.base.command.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.command.usecase.exception.UsecaseErrors

class RequestValidator {
    private val validatorFunctions: MutableList<() -> Result<Unit, DomainItemError>> = mutableListOf()
    fun addValidator(fn: () -> Result<Unit, DomainItemError>) {
        validatorFunctions.add(fn)
    }

    fun validate(): Result<Unit, WebErrors> {
        val errors = mutableListOf<DomainItemError>()

        validatorFunctions.forEach { fn ->
            fn().mapError { errors.add(it) }
        }

        if (errors.isEmpty()) {
            return Ok(Unit)
        }

        return DomainErrors(
            itemErrors = errors
        )
            .let { UsecaseErrors.fromDomainError(it) }
            .let { WebErrors.fromUsecaseError(it) }
            .let { Err(it) }
    }

}
