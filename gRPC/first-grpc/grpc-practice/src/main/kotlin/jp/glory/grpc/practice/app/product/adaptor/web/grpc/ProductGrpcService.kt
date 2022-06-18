package jp.glory.grpc.practice.app.product.adaptor.web.grpc

import com.github.michaelbull.result.*
import io.grpc.stub.StreamObserver
import jp.glory.grpc.practice.app.product.ProductServiceGrpc
import jp.glory.grpc.practice.app.product.ProductServiceOuterClass
import jp.glory.grpc.practice.app.product.ProductServiceOuterClass.GetProductsRequest
import jp.glory.grpc.practice.app.product.ProductServiceOuterClass.ProductResponse
import jp.glory.grpc.practice.app.product.ProductServiceOuterClass.ProductsResponse
import jp.glory.grpc.practice.app.product.usecase.FindProductUseCase
import jp.glory.grpc.practice.app.product.usecase.ProductSearchResult
import jp.glory.grpc.practice.base.adaptor.web.WebError
import jp.glory.grpc.practice.base.adaptor.web.WebResponseUtil
import jp.glory.grpc.practice.base.adaptor.web.toWebError
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class ProductGrpcService(
    private val findProduct: FindProductUseCase
) : ProductServiceGrpc.ProductServiceImplBase() {
    override fun getProducts(
        request: GetProductsRequest,
        responseObserver: StreamObserver<ProductsResponse>) {

        getProductResults()
            .mapBoth(
                success = {
                    responseObserver.onNext(it)
                    responseObserver.onCompleted()
                },
                failure = { WebResponseUtil.responseError(responseObserver, it) }
            )
    }

    override fun getProduct(
        request: ProductServiceOuterClass.GetProductRequest,
        responseObserver: StreamObserver<ProductResponse>
    ) {
        getProductResult(request.id)
            .mapBoth(
                success = {
                    responseObserver.onNext(it)
                    responseObserver.onCompleted()
                },
                failure = { WebResponseUtil.responseError(responseObserver, it) }
            )
    }

    private fun getProductResults(): Result<ProductsResponse, WebError> =
        findProduct.findAll()
            .map { it.products.map { product -> toProductResponse(product) } }
            .map { toProductsResponse(it) }
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(toWebError(it)) }
            )

    private fun getProductResult(
        id: String
    ): Result<ProductResponse, WebError> =
        findProduct.findById(id)
            .map { toProductResponse(it) }
            .mapBoth(
                success = { Ok(it) },
                failure = { Err(toWebError(it)) }
            )

    private fun toProductsResponse(
        products: List<ProductResponse>
    ): ProductsResponse = ProductsResponse.newBuilder()
        .apply {
            addAllProducts(products)
        }
        .build()

    private fun toProductResponse(
        result: ProductSearchResult
    ): ProductResponse = ProductResponse.newBuilder()
        .apply {
            id = result.id
            code = result.code
            name = result.name
            addAllMemberIds(result.memberIDs)
            addAllServiceIds(result.serviceIDs)
        }
        .build()

}
