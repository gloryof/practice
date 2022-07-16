package jp.glory.jfr.practice.app.product.usecase

import com.github.michaelbull.result.*
import jp.glory.jfr.practice.app.base.usecase.UseCase
import jp.glory.jfr.practice.app.base.usecase.UseCaseError
import jp.glory.jfr.practice.app.base.usecase.UseCaseNotFoundError
import jp.glory.jfr.practice.app.base.usecase.toUseCaseError
import jp.glory.jfr.practice.app.product.domain.model.*
import jp.glory.jfr.practice.app.product.domain.repository.ProductRepository
import jp.glory.jfr.practice.app.product.domain.spec.UpdateProductSpec

@UseCase
class UpdateProductUseCase(
    private val repository: ProductRepository
) {
    fun update(input: Input): Result<String, UseCaseError> {
        val event = input.toEvent()

        return findCurrentProduct(event.id)
            .flatMap {
                findPreCheckResult(
                    currentProduct = it,
                    productCode = event.code
                )
            }
            .flatMap {
                validate(
                    event = event,
                    preCheckResult = it
                )
            }
            .flatMap { save(it) }
            .mapBoth(
                success = { Ok(it.value) },
                failure = { Err(it) }
            )
    }

    private fun findCurrentProduct(
        id: ProductID
    ): Result<Product, UseCaseError> =
        repository.findById(id)
            .flatMap {
                if (it == null) {
                    return Err(
                        UseCaseNotFoundError(
                            resourceName = UseCaseNotFoundError.ResourceName.Product,
                            idValue = id.value
                        )
                    )
                } else {
                    Ok(it)
                }
            }
            .mapError { toUseCaseError(it) }

    private fun findPreCheckResult(
        currentProduct: Product,
        productCode: ProductCode
    ): Result<UpdateProductPreCheckResult, UseCaseError> =
        repository.findByProductCode(productCode)
            .map {
                UpdateProductPreCheckResult(
                    targetProduct = currentProduct,
                    sameCodeProducts = it
                )
            }
            .mapError { toUseCaseError(it) }

    private fun validate(
        event: UpdateProductEvent,
        preCheckResult: UpdateProductPreCheckResult
    ): Result<UpdateProductEvent, UseCaseError> =
        UpdateProductSpec.validate(
            event = event,
            preCheckResult = preCheckResult
        )
            .mapBoth(
                success = { Ok(event) },
                failure = { Err(toUseCaseError(it)) }
            )

    private fun save(
        event: UpdateProductEvent
    ): Result<ProductID, UseCaseError> =
        repository.save(event)
            .mapError { toUseCaseError(it) }

    class Input(
        private val id: String,
        private val code: String,
        private val name: String,
        private val memberIDs: List<String>,
        private val serviceIDs: List<String>
    ) {
        fun toEvent(): UpdateProductEvent =
            UpdateProductEvent(
                id = ProductID(id),
                code = ProductCode(code),
                name = ProductName(name),
                memberIDs = MemberIds(memberIDs.map { MemberID(it) }),
                serviceIDs = ServiceIds(serviceIDs.map { ServiceID(it) }),
            )
    }
}
