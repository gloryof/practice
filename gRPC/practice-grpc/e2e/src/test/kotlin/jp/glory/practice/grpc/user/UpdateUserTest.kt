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


class UpdateUserTest {
    @Test
    fun success() {
        val user = UserTestScript.registerUser(
            userName = "before-user",
            birthday = "1900-01-01"
        )
        val expectedUserName = "test-user"
        val expectedBirthday = "1986-12-16"
        val channel = TestEnvironment.getChannel()
        val sut = UpdateUserServiceGrpc.newBlockingStub(channel)

        val request = UpdateUserRequest.newBuilder()
            .setUserId(user.userId)
            .setUserName(expectedUserName)
            .setBirthday(expectedBirthday)
            .build()

        sut.update(request)

        val getUserStub = GetUserServiceGrpc.newBlockingStub(channel)
        val getUserRequest = GetUserByIdRequest.newBuilder()
            .setUserId(user.userId)
            .build()
        val actualUser = getUserStub.getUserById(getUserRequest)

        Assertions.assertEquals(expectedUserName, actualUser.userName)
        Assertions.assertEquals(expectedBirthday, actualUser.birthday)
    }
}