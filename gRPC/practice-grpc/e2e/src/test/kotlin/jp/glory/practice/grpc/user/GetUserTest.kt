package jp.glory.practice.grpc.user

import jp.glory.practice.grpc.TestEnvironment
import jp.glory.practice.grpc.user.adaptor.web.GetUserServiceGrpc
import jp.glory.practice.grpc.user.adaptor.web.RegisterUserServiceGrpc
import jp.glory.practice.grpc.user.adaptor.web.UpdateUserServiceGrpc
import jp.glory.practice.grpc.user.adaptor.web.User.GetUserByIdRequest
import jp.glory.practice.grpc.user.adaptor.web.User.RegisterUserRequest
import jp.glory.practice.grpc.user.adaptor.web.User.UpdateUserRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class GetUserTest {
    @Test
    fun success() {
        val user = UserTestScript.registerUser(
            userName = "get-user",
            birthday = "1986-12-16"
        )
        val channel = TestEnvironment.getChannel()

        val getUserStub = GetUserServiceGrpc.newBlockingStub(channel)
        val getUserRequest = GetUserByIdRequest.newBuilder()
            .setUserId(user.userId)
            .build()
        val actualUser = getUserStub.getUserById(getUserRequest)

        Assertions.assertEquals(user.userId, actualUser.userId)
        Assertions.assertEquals(user.userName, actualUser.userName)
        Assertions.assertEquals(user.birthday, actualUser.birthday)
    }
}