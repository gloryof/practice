package jp.glory.practice.graphql.document.product.adaptor.web.controller

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import jp.glory.practice.graphql.document.base.adaptor.web.WebError
import jp.glory.practice.graphql.document.base.adaptor.web.toWebError
import jp.glory.practice.graphql.document.product.adaptor.web.graphql.schema.Product
import jp.glory.practice.graphql.document.product.adaptor.web.graphql.schema.RegisterProductInput
import jp.glory.practice.graphql.document.product.adaptor.web.graphql.schema.RegisterProductResult
import jp.glory.practice.graphql.document.product.usecase.FindProductUseCase
import jp.glory.practice.graphql.document.product.usecase.ProductSearchResult
import jp.glory.practice.graphql.document.product.usecase.RegisterProductUseCase
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class RegisterProductController(
    private val register: RegisterProductUseCase,
    private val findProduct: FindProductUseCase,
) {
    @MutationMapping
    fun registerProduct(
        @Argument input: RegisterProductInput
    ): RegisterProductResult =
        register(input)
            .mapBoth(
                success = { RegisterProductResult(it) },
                failure = { throw it.createException() }
            )

    @SchemaMapping
    fun product(
        result: RegisterProductResult
    ): Product =
        findProductById(result.id)
            .mapBoth(
                success = { Product(it) },
                failure = { throw it.createException() }
            )

    private fun findProductById(id: String): Result<ProductSearchResult, WebError> =
        findProduct.findById(id)
            .mapError { toWebError(it) }

    private fun register(
        input: RegisterProductInput
    ): Result<String, WebError> =
        register.register(
            input = toUseCaseInput(input)
        )
            .mapError { toWebError(it) }

    private fun toUseCaseInput(
        input: RegisterProductInput
    ): RegisterProductUseCase.Input =
        RegisterProductUseCase.Input(
            code = input.code,
            name = input.name,
            memberIDs = input.memberIDs,
            serviceIDs = input.serviceIDs
        )
}
