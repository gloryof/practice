package jp.glory.practice.grpc.user.adaptor.web

import io.grpc.stub.StreamObserver
import jp.glory.practice.grpc.base.adaptor.web.GrpcRequestUtil
import jp.glory.practice.grpc.user.adaptor.web.User.RegisterUserRequest
import jp.glory.practice.grpc.user.adaptor.web.User.RegisterUserResponse
import jp.glory.practice.grpc.user.useCase.RegisterUser
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class RegisterUserGrpcApi(
    private val registerUser: RegisterUser
) : RegisterUserServiceGrpc.RegisterUserServiceImplBase() {

    override fun register(
        request: RegisterUserRequest,
        responseObserver: StreamObserver<RegisterUserResponse>
    ) {
        registerUser.register(toUseCaseInput(request))
            .let { toResponse(it) }
            .let { responseObserver.onNext(it) }
            .let { responseObserver.onCompleted() }
    }

    private fun toUseCaseInput(
        request: RegisterUserRequest
    ): RegisterUser.Input =
        RegisterUser.Input(
            userName = request.userName,
            birthday = GrpcRequestUtil.toLocalDate(request.birthday)
        )

    private fun toResponse(
        output: RegisterUser.Output
    ): RegisterUserResponse =
        RegisterUserResponse.newBuilder()
            .setUserId(output.id)
            .build()
}