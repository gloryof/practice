package jp.glory.grpc.practice

import io.grpc.ServerBuilder
import io.grpc.ServerServiceDefinition
import io.grpc.protobuf.services.ProtoReflectionService
import jp.glory.grpc.practice.app.product.ProductModule
import jp.glory.grpc.practice.app.product.adaptor.web.grpc.ProductGrpcService
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.component.inject

class GrpcPracticeApplication : KoinComponent {
	fun runServer() {
		startKoin {
			modules(ProductModule.productModule())
		}

		val productService by inject<ProductGrpcService>()
		val services = listOf<ServerServiceDefinition>(
			productService.bindService()
		)

		ServerBuilder.forPort(6565)
			.addServices(services)
			.addService(ProtoReflectionService.newInstance())
			.build()
			.start()
			.awaitTermination()
	}

}

fun main(args: Array<String>) {
	GrpcPracticeApplication()
		.runServer()
}
