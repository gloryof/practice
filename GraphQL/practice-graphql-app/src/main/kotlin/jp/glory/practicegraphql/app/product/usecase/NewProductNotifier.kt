package jp.glory.practicegraphql.app.product.usecase

import com.github.michaelbull.result.Result
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.product.domain.model.Product
import jp.glory.practicegraphql.app.product.domain.model.ProductID
import reactor.core.publisher.Flux

interface NewProductNotifier {
    fun register(product: Product): Result<ProductID, DomainUnknownError>
    fun subscribe(): Flux<ProductSearchResult>
}
