package jp.glory.app.open_telemetry.practice.product.adaptor.web

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.Result
import jp.glory.app.open_telemetry.practice.base.adaptor.web.error.WebError
import jp.glory.app.open_telemetry.practice.base.adaptor.web.error.WebErrorHelper
import jp.glory.app.open_telemetry.practice.product.usecase.FindProductUseCase
import jp.glory.app.open_telemetry.practice.product.usecase.ProductSearchResult
import jp.glory.app.open_telemetry.practice.product.usecase.RegisterProductUseCase
import jp.glory.app.open_telemetry.practice.product.usecase.UpdateProductUseCase

class ProductApi(
    private val findProductUseCase: FindProductUseCase,
    private val registerProductUseCase: RegisterProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) {
    fun findAll(): Result<ProductsResponse, WebError> =
        findProductUseCase.findAll()
            .map {
                ProductsResponse(
                    products = it.results.map { result -> toProductResponse(result) }
                )
            }
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(WebErrorHelper.create(it)) }
            )

    fun findById(
       id: String
    ): Result<ProductResponse, WebError> =
        findProductUseCase.findById(id)
            .map { toProductResponse(it) }
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(WebErrorHelper.create(it)) }
            )

    fun register(
        request: RegisterProductRequest
    ): Result<RegisterProductResponse, WebError> =
        toRegisterInput(request)
            .let { registerProductUseCase.register(it) }
            .map { RegisterProductResponse(it) }
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(WebErrorHelper.create(it)) }
            )

    fun update(
        id: String,
        request: UpdateProductRequest
    ): Result<UpdateProductResponse, WebError> =
        toUpdateInput(id, request)
            .let { updateProductUseCase.update(it) }
            .map { UpdateProductResponse(it) }
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(WebErrorHelper.create(it)) }
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
