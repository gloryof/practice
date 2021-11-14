package jp.glory.practicegraphql.app.product.adaptor.web.controller

import com.github.michaelbull.result.*
import jp.glory.practicegraphql.app.base.adaptor.web.error.NotFoundError
import jp.glory.practicegraphql.app.base.adaptor.web.error.WebError
import jp.glory.practicegraphql.app.base.adaptor.web.error.toWebError
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Product
import jp.glory.practicegraphql.app.product.usecase.FindProductUseCase
import jp.glory.practicegraphql.app.product.usecase.ProductSearchResult
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class ProductController(
    private val useCase: FindProductUseCase
) {
    @QueryMapping
    fun product(
        @Argument id: String,
    ): Product =
        findById(id)
            .mapBoth(
                success = { Product(it) },
                failure = { throw it.createException() }
            )

    private fun findById(id: String): Result<ProductSearchResult, WebError> =
        useCase.findById(id)
            .mapError { toWebError(it) }
            .flatMap {
                if (it == null) {
                    Err(NotFoundError("Can not found product"))
                } else {
                    Ok(it)
                }
            }
}
