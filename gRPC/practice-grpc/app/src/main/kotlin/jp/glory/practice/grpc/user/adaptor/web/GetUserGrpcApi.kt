package jp.glory.practice.grpc.user.adaptor.web

import io.grpc.stub.StreamObserver
import jp.glory.practice.grpc.user.adaptor.web.User.GetUserByIdRequest
import jp.glory.practice.grpc.user.adaptor.web.User.UserDetailResponse
import jp.glory.practice.grpc.user.useCase.GetUser
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class GetUserGrpcApi(
    private val getUser: GetUser
) : GetUserServiceGrpc.GetUserServiceImplBase() {
    override fun getUserById(
        request: GetUserByIdRequest,
        responseObserver: StreamObserver<UserDetailResponse>
    ) {
        getUser.findById(request.userId)
            .let { toResponse(it) }
            .let { responseObserver.onNext(it) }
            .let { responseObserver.onCompleted() }
    }

    private fun toResponse(output: GetUser.Output): UserDetailResponse =
        UserDetailResponse.newBuilder()
            .setUserId(output.userId)
            .setUserName(output.userName)
            .setBirthday(output.birthday.toString())
            .build()
}