package jp.glory.chaos.mesh.practice.app.product.adaptor.web

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import jp.glory.chaos.mesh.practice.app.base.adaptor.web.error.WebExceptionHelper
import jp.glory.chaos.mesh.practice.app.product.usecase.FindProductUseCase
import jp.glory.chaos.mesh.practice.app.product.usecase.ProductSearchResult
import jp.glory.chaos.mesh.practice.app.product.usecase.RegisterProductUseCase
import jp.glory.chaos.mesh.practice.app.product.usecase.UpdateProductUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductApi(
    private val findProductUseCase: FindProductUseCase,
    private val registerProductUseCase: RegisterProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) {
    @GetMapping
    fun findAll(): ResponseEntity<ProductsResponse> =
        findProductUseCase.findAll()
            .map {
                ProductsResponse(
                    products = it.results.map { result -> toProductResponse(result) }
                )
            }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: String
    ): ResponseEntity<ProductResponse> =
        findProductUseCase.findById(id)
            .map { toProductResponse(it) }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @PostMapping
    fun register(
        @RequestBody request: RegisterProductRequest
    ): ResponseEntity<RegisterProductResponse> =
        toRegisterInput(request)
            .let { registerProductUseCase.register(it) }
            .map { RegisterProductResponse(it) }
            .mapBoth(
                success = { ResponseEntity.status(HttpStatus.CREATED).body(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: UpdateProductRequest
    ): ResponseEntity<UpdateProductResponse> =
        toUpdateInput(id, request)
            .let { updateProductUseCase.update(it) }
            .map { UpdateProductResponse(it) }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    private fun toProductResponse(
        result: ProductSearchResult
    ): ProductResponse = ProductResponse(
        id = result.id,
        code = result.code,
        name = result.name,
        memberIDs = result.memberIDs,
        serviceIDs = result.serviceIDs
    )

    private fun toRegisterInput(
        request: RegisterProductRequest
    ): RegisterProductUseCase.Input = RegisterProductUseCase.Input(
        code = request.code,
        name = request.name,
        memberIDs = request.memberIDs,
        serviceIDs = request.serviceIDs
    )

    private fun toUpdateInput(
        id: String,
        request: UpdateProductRequest
    ): UpdateProductUseCase.Input = UpdateProductUseCase.Input(
        id = id,
        code = request.code,
        name = request.name,
        memberIDs = request.memberIDs,
        serviceIDs = request.serviceIDs
    )

}

data class ProductsResponse(
    val products: List<ProductResponse>
)

data class ProductResponse(
    val id: String,
    val code: String,
    val name: String,
    val memberIDs: List<String>,
    val serviceIDs: List<String>
)

data class RegisterProductRequest(
    val code: String,
    val name: String,
    val memberIDs: List<String>,
    val serviceIDs: List<String>
)

data class RegisterProductResponse(
    val id: String
)

data class UpdateProductRequest(
    val code: String,
    val name: String,
    val memberIDs: List<String>,
    val serviceIDs: List<String>
)

data class UpdateProductResponse(
    val id: String
)
