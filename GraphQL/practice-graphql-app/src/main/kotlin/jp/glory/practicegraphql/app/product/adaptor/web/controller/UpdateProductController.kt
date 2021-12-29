package jp.glory.practicegraphql.app.product.adaptor.web.controller

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import jp.glory.practicegraphql.app.base.adaptor.web.error.WebError
import jp.glory.practicegraphql.app.base.adaptor.web.error.toWebError
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Product
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.UpdateProductInput
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.UpdateProductResult
import jp.glory.practicegraphql.app.product.usecase.FindProductUseCase
import jp.glory.practicegraphql.app.product.usecase.ProductSearchResult
import jp.glory.practicegraphql.app.product.usecase.UpdateProductUseCase
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class UpdateProductController(
    private val update: UpdateProductUseCase,
    private val findProduct: FindProductUseCase
) {
    @MutationMapping
    @NewSpan
    fun updateProduct(
        @Argument input: UpdateProductInput
    ): UpdateProductResult =
        update(input)
            .mapBoth(
                success = { UpdateProductResult(it) },
                failure = { throw it.createException() }
            )

    private fun update(
        input: UpdateProductInput
    ): Result<String, WebError> =
        update.update(
            input = toUseCaseInput(input)
        )
            .mapError { toWebError(it) }

    @SchemaMapping
    @NewSpan
    fun product(
        result: UpdateProductResult
    ): Product =
        findProductById(result.id)
            .mapBoth(
                success = { Product(it) },
                failure = { throw it.createException() }
            )

    private fun findProductById(id: String): Result<ProductSearchResult, WebError> =
        findProduct.findById(id)
            .mapError { toWebError(it) }

    private fun toUseCaseInput(
        input: UpdateProductInput
    ): UpdateProductUseCase.Input =
        UpdateProductUseCase.Input(
            id = input.id,
            code = input.code,
            name = input.name,
            memberIDs = input.memberIDs,
            serviceIDs = input.serviceIDs
        )
}
