package jp.glory.app.open_telemetry.practice.product.usecase

import com.github.michaelbull.result.*
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseError
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseTelemetry
import jp.glory.app.open_telemetry.practice.base.usecase.toUseCaseError
import jp.glory.app.open_telemetry.practice.product.domain.model.*
import jp.glory.app.open_telemetry.practice.product.domain.repository.ProductRepository
import jp.glory.app.open_telemetry.practice.product.domain.spec.RegisterProductSpec

class RegisterProductUseCase(
    private val repository: ProductRepository,
    private val useCaseTelemetry: UseCaseTelemetry
) {
    fun register(input: Input): Result<String, UseCaseError> {
        useCaseTelemetry.registerUseCaseAttribute(
            useCaseName = "registerProduct",
            methodName = "register"
        )
        val event = input.toEvent()

        return findPreCheckResult(event.code)
            .flatMap { validate(event, it) }
            .flatMap { registerEvent(event) }
            .mapBoth(
                success = { Ok(it.value) },
                failure = { Err(it) }
            )
    }

    private fun findPreCheckResult(
        productCode: ProductCode
    ): Result<RegisterProductPreCheckResult, UseCaseError> =
        repository.findByProductCode(productCode)
            .map { RegisterProductPreCheckResult(it) }
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(toUseCaseError(it)) }
            )

    private fun validate(
        event: RegisterProductEvent,
        preCheckResult: RegisterProductPreCheckResult
    ): Result<RegisterProductEvent, UseCaseError> =
        RegisterProductSpec.validate(
            event = event,
            preCheckResult = preCheckResult
        )
            .mapBoth(
                success = { Ok(event) },
                failure = { Err(toUseCaseError(it)) }
            )

    private fun registerEvent(
        event: RegisterProductEvent
    ): Result<ProductID, UseCaseError> {
        val id = ProductIdGenerator.generate()
        return repository.register(
            id = id,
            event = event
        )
            .mapBoth(
                success = { Ok(id) },
                failure = { Err(toUseCaseError(it)) }
            )
    }

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
