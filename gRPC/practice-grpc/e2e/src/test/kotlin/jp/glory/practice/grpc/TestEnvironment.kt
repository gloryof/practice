package jp.glory.practice.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

object TestEnvironment {
    private val env = System.getenv()
    private val address = env["TEST_HOST"] ?: "localhost"
    private val port = env["TEST_PORT"]?.toInt() ?: 6565

    fun getChannel (): ManagedChannel =
        ManagedChannelBuilder.forAddress(address, port)
            .usePlaintext()
            .build()
}