package jp.glory.open_feature.practice.product.use_case

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import jp.glory.open_feature.practice.base.use_case.UseCase
import jp.glory.open_feature.practice.base.use_case.UseCaseError
import jp.glory.open_feature.practice.base.use_case.UseCaseNotFoundError
import jp.glory.open_feature.practice.product.domain.model.*
import jp.glory.open_feature.practice.product.domain.repository.ProductRepository

@UseCase
class UpdateProductUseCase(
    private val repository: ProductRepository
) {
    fun update(input: Input): String =
        input.toEvent()
            .let { save(it) }
            .value

    private fun findCurrentProduct(
        id: ProductID
    ): Result<Product, UseCaseError> =
        repository.findById(id)
            ?.let { Ok(it) }
            ?: Err(
                UseCaseNotFoundError(
                    resourceName = UseCaseNotFoundError.ResourceName.Product,
                    idValue = id.value
                )
            )

    private fun save(
        event: UpdateProductEvent
    ): ProductID =
        repository.save(event)

    class Input(
        val id: String,
        val code: String,
        val name: String,
        val memberIDs: List<String>,
        val serviceIDs: List<String>
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