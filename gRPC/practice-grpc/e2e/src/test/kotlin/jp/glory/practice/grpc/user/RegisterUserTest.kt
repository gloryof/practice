package jp.glory.practice.grpc.user

import jp.glory.practice.grpc.TestEnvironment
import jp.glory.practice.grpc.user.adaptor.web.GetUserServiceGrpc
import jp.glory.practice.grpc.user.adaptor.web.RegisterUserServiceGrpc
import jp.glory.practice.grpc.user.adaptor.web.User.GetUserByIdRequest
import jp.glory.practice.grpc.user.adaptor.web.User.RegisterUserRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class RegisterUserTest {
    @Test
    fun success() {
        val expectedUserName = "test-user"
        val expectedBirthday = "1986-12-16"
        val channel = TestEnvironment.getChannel()
        val sut = RegisterUserServiceGrpc.newBlockingStub(channel)

        val request = RegisterUserRequest.newBuilder()
            .setUserName(expectedUserName)
            .setBirthday(expectedBirthday)
            .build()

        val actual = sut.register(request)

        val actualId = actual.userId
        Assertions.assertTrue(actualId.isNotBlank())

        val getUserStub = GetUserServiceGrpc.newBlockingStub(channel)
        val getUserRequest = GetUserByIdRequest.newBuilder()
            .setUserId(actualId)
            .build()
        val actualUser = getUserStub.getUserById(getUserRequest)

        Assertions.assertEquals(expectedUserName, actualUser.userName)
        Assertions.assertEquals(expectedBirthday, actualUser.birthday)
    }
}