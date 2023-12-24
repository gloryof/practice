package jp.glory.practice.grpc.user.adaptor.web

import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import jp.glory.practice.grpc.base.adaptor.web.GrpcRequestUtil
import jp.glory.practice.grpc.user.adaptor.web.User.UpdateUserRequest
import jp.glory.practice.grpc.user.useCase.UpdateUser
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class UpdateUserGrpcApi(
    private val updateUser: UpdateUser
) : UpdateUserServiceGrpc.UpdateUserServiceImplBase() {

    override fun update(
        request: UpdateUserRequest,
        responseObserver: StreamObserver<Empty>
    ) {
        updateUser.update(toUseCaseInput(request))
            .let { responseObserver.onNext(Empty.getDefaultInstance()) }
            .let { responseObserver.onCompleted() }
    }

    private fun toUseCaseInput(
        request: UpdateUserRequest
    ): UpdateUser.Input =
        UpdateUser.Input(
            userId = request.userId,
            userName = request.userName,
            birthday = GrpcRequestUtil.toLocalDate(request.birthday)
        )
}