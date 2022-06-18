package jp.glory.grpc.practice.base.adaptor.web

import io.grpc.protobuf.StatusProto
import io.grpc.stub.StreamObserver

object WebResponseUtil {
    fun <T> responseError(
        responseObserver: StreamObserver<T>,
        error: WebError
    ) {
        error
            .run {
                getStatus()
            }
            .let { responseObserver.onError(StatusProto.toStatusException(it)) }
    }
}
