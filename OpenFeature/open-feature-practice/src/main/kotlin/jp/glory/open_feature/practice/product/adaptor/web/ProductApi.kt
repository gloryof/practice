package jp.glory.open_feature.practice.product.adaptor.web

import com.github.michaelbull.result.mapBoth
import jp.glory.open_feature.practice.base.adaptor.web.EndpointConst
import jp.glory.open_feature.practice.base.adaptor.web.WebApi
import jp.glory.open_feature.practice.base.adaptor.web.WebExceptionHelper
import jp.glory.open_feature.practice.product.use_case.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@WebApi
@RequestMapping(EndpointConst.Product.product)
class ProductApi(
    private val findProduct: FindProductUseCase,
    private val registerProductUseCase: RegisterProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) {

    @GetMapping
    fun findAllProducts(): Products =
        findProduct.findAll()
            .let { it.products.map { prod -> Product(prod) } }
            .let { Products(it) }

    @GetMapping("/{id}")
    fun findProduct(
        @PathVariable("id") id: String,
    ): Product =
        findProduct.findById(id)
            .mapBoth(
                success = { Product(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @PostMapping
    fun register(
        @RequestBody request: RegisterProductInput
    ): ResponseEntity<RegisterProductResult> =
        registerProductUseCase.register(
            RegisterProductUseCase.Input(
                code = request.code,
                name = request.name,
                memberIDs = request.memberIDs,
                serviceIDs = request.serviceIDs
            )
        )
            .let {
                ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(RegisterProductResult(it))
            }

    @PutMapping("/{id}")
    fun updateProduct(
        @RequestBody request: UpdateProductInput,
        @PathVariable("id") id: String,
    ): UpdateProductResult =
        updateProductUseCase.update(
            UpdateProductUseCase.Input(
                id = id,
                code = request.code,
                name = request.name,
                memberIDs = request.memberIDs,
                serviceIDs = request.serviceIDs
            )
        )
            .let { UpdateProductResult(it) }

    class Products(
        val products: List<Product>
    )

    class Product(
        val id: String,
        val code: String,
        val name: String,
        val memberIds: List<String>,
        val serviceIds: List<String>
    ) {
        constructor(result: ProductSearchResult) : this(
            id = result.id,
            code = result.code,
            name = result.name,
            memberIds = result.memberIDs,
            serviceIds = result.serviceIDs,
        )
    }
    class RegisterProductInput(
        val code: String,
        val name: String,
        val memberIDs: List<String>,
        val serviceIDs: List<String>
    )

    class RegisterProductResult(
        val id: String
    )

    class UpdateProductInput(
        val code: String,
        val name: String,
        val memberIDs: List<String>,
        val serviceIDs: List<String>
    )

    class UpdateProductResult(
        val id: String
    )
}
