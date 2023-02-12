package jp.glory.open_feature.practice.product.use_case

import jp.glory.open_feature.practice.base.use_case.UseCase
import jp.glory.open_feature.practice.product.domain.model.*
import jp.glory.open_feature.practice.product.domain.repository.ProductRepository

@UseCase
class RegisterProductUseCase(
    private val repository: ProductRepository
) {
    fun register(input: Input): String =
        input.toEvent()
            .let { register(it) }
            .value

    private fun register(
        event: RegisterProductEvent
    ): ProductID =
        ProductIdGenerator.generate()
            .let {
                repository.register(
                    id = it,
                    event = event
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