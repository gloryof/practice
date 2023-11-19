package jp.glory.practice.measure.architecture.product.adaptor.web.controller

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.Result
import jp.glory.practice.measure.architecture.base.adaptor.web.WebError
import jp.glory.practice.measure.architecture.base.adaptor.web.toWebError
import jp.glory.practice.measure.architecture.product.adaptor.web.graphql.schema.Product
import jp.glory.practice.measure.architecture.product.adaptor.web.graphql.schema.Products
import jp.glory.practice.measure.architecture.product.adaptor.web.graphql.schema.RegisterProductInput
import jp.glory.practice.measure.architecture.product.adaptor.web.graphql.schema.RegisterProductResult
import jp.glory.practice.measure.architecture.product.adaptor.web.graphql.schema.UpdateProductInput
import jp.glory.practice.measure.architecture.product.adaptor.web.graphql.schema.UpdateProductResult
import jp.glory.practice.measure.architecture.product.usecase.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/products")
class ProductController(
    private val findProduct: FindProductUseCase,
    private val register: RegisterProductUseCase,
    private val update: UpdateProductUseCase,
) {

    @GetMapping
    fun findAllProducts(): Products =
        findAll()
            .map { it.products.map { prod -> Product(prod) } }
            .mapBoth(
                success = { Products(it) },
                failure = { throw it.createException() }
            )

    @GetMapping("/{id}")
    fun findProduct(
        @PathVariable("id") id: String,
    ): Product =
        findById(id)
            .mapBoth(
                success = { Product(it) },
                failure = { throw it.createException() }
            )

    @PostMapping
    fun registerProduct(
        @RequestBody input: RegisterProductInput
    ): RegisterProductResult =
        register(input)
            .mapBoth(
                success = { RegisterProductResult(it) },
                failure = { throw it.createException() }
            )

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable("id") id: String,
        @RequestBody input: UpdateProductInput
    ): UpdateProductResult =
        update(input)
            .mapBoth(
                success = { UpdateProductResult(it) },
                failure = { throw it.createException() }
            )
    private fun findAll(): Result<ProductsSearchResult, WebError> =
        findProduct.findAll()
            .mapError { toWebError(it) }

    private fun findById(id: String): Result<ProductSearchResult, WebError> =
        findProduct.findById(id)
            .mapError { toWebError(it) }

    private fun register(
        input: RegisterProductInput
    ): Result<String, WebError> =
        register.register(
            input = toRegisterUseCaseInput(input)
        )
            .mapError { toWebError(it) }

    private fun update(
        input: UpdateProductInput
    ): Result<String, WebError> =
        update.update(
            input = toUpdateUseCaseInput(input)
        )
            .mapError { toWebError(it) }

    private fun toRegisterUseCaseInput(
        input: RegisterProductInput
    ): RegisterProductUseCase.Input =
        RegisterProductUseCase.Input(
            code = input.code,
            name = input.name,
            memberIDs = input.memberIDs,
            serviceIDs = input.serviceIDs
        )

    private fun toUpdateUseCaseInput(
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
