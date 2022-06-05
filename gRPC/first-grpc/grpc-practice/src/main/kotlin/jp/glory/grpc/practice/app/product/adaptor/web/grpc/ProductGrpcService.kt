package jp.glory.grpc.practice.app.product.adaptor.web.grpc

import io.grpc.stub.StreamObserver
import jp.glory.grpc.practice.app.product.ProductServiceGrpc
import jp.glory.grpc.practice.app.product.ProductServiceOuterClass.GetProductsRequest
import jp.glory.grpc.practice.app.product.ProductServiceOuterClass.ProductResponse
import jp.glory.grpc.practice.app.product.ProductServiceOuterClass.ProductsResponse
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class ProductGrpcService : ProductServiceGrpc.ProductServiceImplBase() {
    override fun getProducts(
        request: GetProductsRequest,
        responseObserver: StreamObserver<ProductsResponse>) {

        responseObserver.onNext(createProductsResponse())
        responseObserver.onCompleted()
    }

    private fun createProductsResponse(): ProductsResponse {
        val product = ProductResponse.newBuilder()
            .apply {
                id = "test-id"
                code = "test-code"
                name = "test-name"
                addMemberIds("test-member-id")
                addServiceIds("service-id")
            }
            .build()

        return ProductsResponse.newBuilder()
            .apply {
                addProducts(product)
            }
            .build()
    }
}
