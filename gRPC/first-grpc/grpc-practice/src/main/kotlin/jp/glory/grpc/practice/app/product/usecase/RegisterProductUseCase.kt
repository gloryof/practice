package jp.glory.grpc.practice.app.product.usecase

import com.github.michaelbull.result.*
import jp.glory.grpc.practice.app.product.domain.model.*
import jp.glory.grpc.practice.base.usecase.UseCaseError
import jp.glory.grpc.practice.base.usecase.UseCaseNotFoundError
import jp.glory.grpc.practice.base.usecase.toUseCaseError
import jp.glory.grpc.practice.app.product.domain.repository.ProductRepository
import jp.glory.grpc.practice.app.product.domain.spec.RegisterProductSpec

class RegisterProductUseCase(
    private val repository: ProductRepository
) {
    fun register(input: Input): Result<String, UseCaseError> {
        val event = input.toEvent()

        return findPreCheckResult(event.code)
            .flatMap { validate(event, it) }
            .flatMap { register(event) }
            .flatMap { notify(it) }
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

    private fun register(
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

    private fun notify(id: ProductID): Result<ProductID, UseCaseError> =
        repository.findById(id)
            .map { it ?: return createNotFound(id.value) }
            .mapBoth(
                success = { Ok(id) },
                failure = { Err(toUseCaseError(it)) }
            )

    private fun createNotFound(id: String): Err<UseCaseNotFoundError> =
        Err(
            UseCaseNotFoundError(
                resourceName = UseCaseNotFoundError.ResourceName.Product,
                idValue = id
            )
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
