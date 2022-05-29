package jp.glory.grpc.practice.app.product.usecase

import com.github.michaelbull.result.Result
import jp.glory.grpc.practice.base.domain.DomainUnknownError
import jp.glory.grpc.practice.app.product.domain.model.Product
import jp.glory.grpc.practice.app.product.domain.model.ProductID
import reactor.core.publisher.Flux

interface NewProductNotifier {
    fun register(product: Product): Result<ProductID, DomainUnknownError>
    fun subscribe(): Flux<ProductSearchResult>
}
