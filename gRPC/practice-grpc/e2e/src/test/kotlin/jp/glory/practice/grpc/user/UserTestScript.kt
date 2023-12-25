package jp.glory.practice.grpc.user

import jp.glory.practice.grpc.TestEnvironment
import jp.glory.practice.grpc.user.adaptor.web.RegisterUserServiceGrpc
import jp.glory.practice.grpc.user.adaptor.web.User.RegisterUserRequest
import jp.glory.practice.grpc.user.adaptor.web.User.RegisterUserResponse

object UserTestScript {
    fun registerUser(
        userName: String = "test-user",
        birthday: String = "1986-12-16"
    ): UserData {
        val request = RegisterUserRequest.newBuilder()
            .setUserName(userName)
            .setBirthday(birthday)
            .build()
        val channel = TestEnvironment.getChannel()

        val response = RegisterUserServiceGrpc.newBlockingStub(channel)
            .register(request)

        return UserData(
            userId = response.userId,
            userName = userName,
            birthday = birthday
        )
    }

    class UserData(
        val userId: String,
        val userName: String,
        val birthday: String
    )
}