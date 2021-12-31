package jp.glory.practicegraphql.app.product.adaptor.notifier

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.product.domain.model.Product
import jp.glory.practicegraphql.app.product.domain.model.ProductID
import jp.glory.practicegraphql.app.product.usecase.NewProductNotifier
import jp.glory.practicegraphql.app.product.usecase.ProductSearchResult
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Component
class NewProductNotifierImpl : NewProductNotifier {
    private val listener: Listener = Listener()
    private val flux: Flux<ProductSearchResult>

    init {
        val flux: Flux<ProductSearchResult> = Flux.create {
            listener.registerSink(it)
        }

        this.flux = flux.share()
    }

    override fun register(product: Product): Result<ProductID, DomainUnknownError> {
        listener.add(product)

        return Ok(product.id)
    }

    override fun subscribe(): Flux<ProductSearchResult> = flux

    inner class Listener(private var sink: FluxSink<ProductSearchResult>? = null) {
        fun registerSink(sink: FluxSink<ProductSearchResult>?) {
            this.sink = sink
        }

        fun add(product: Product) =
            sink?.next(ProductSearchResult(product))
    }
}
