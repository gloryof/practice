package jp.glory.practicegraphql.app.product.usecase

import com.github.michaelbull.result.*
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.base.domain.SpecError
import jp.glory.practicegraphql.app.base.usecase.UseCase
import jp.glory.practicegraphql.app.base.usecase.UseCaseError
import jp.glory.practicegraphql.app.base.usecase.toUseCaseError
import jp.glory.practicegraphql.app.product.domain.model.*
import jp.glory.practicegraphql.app.product.domain.repository.ProductRepository
import jp.glory.practicegraphql.app.product.domain.spec.RegisterProductSpec

@UseCase
class RegisterProductUseCase(
    private val repository: ProductRepository
) {
    fun register(input: Input): Result<String, UseCaseError> {
        val event = input.toEvent()

        return findPreCheckResult(event.code)
            .flatMap {
                validate(
                    event = event,
                    preCheckResult = it
                )
            }
            .flatMap {
                repository.register(
                    id = ProductIdGenerator.generate(),
                    event = it
                )
            }
            .mapBoth(
                success = { Ok(it.value) },
                failure = { Err(toUseCaseError(it)) }
            )
    }

    private fun findPreCheckResult(
        productCode: ProductCode
    ): Result<RegisterProductPreCheckResult, DomainUnknownError> =
        repository.findByProductCode(productCode)
            .map { RegisterProductPreCheckResult(it) }

    private fun validate(
        event: RegisterProductEvent,
        preCheckResult: RegisterProductPreCheckResult
    ): Result<RegisterProductEvent, SpecError> =
        RegisterProductSpec.validate(
            event = event,
            preCheckResult = preCheckResult
        )
            .mapBoth(
                success = { Ok(event) },
                failure = { Err(it) }
            )

    class Input(
        private val code: String,
        private val name: String,
        private val memberIDs: List<String>,
        private val serviceIDs: List<String>
    ) {
        fun toEvent(): RegisterProductEvent =
            RegisterProductEvent(
                code = ProductCode(code),
                name = ProductName(name),
                memberIDs = MemberIds(memberIDs.map { MemberID(it) }),
                serviceIDs = ServiceIds(serviceIDs.map { ServiceID(it) }),
            )
    }
}
