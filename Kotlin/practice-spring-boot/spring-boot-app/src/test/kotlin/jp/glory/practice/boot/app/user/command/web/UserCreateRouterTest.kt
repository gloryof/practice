package jp.glory.practice.boot.app.user.command.web

import com.github.michaelbull.result.Ok
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.glory.practice.boot.app.base.common.web.WebErrors
import jp.glory.practice.boot.app.base.common.web.WebItemError
import jp.glory.practice.boot.app.base.common.web.WebItemErrorType
import jp.glory.practice.boot.app.test.tool.MockMvcCreator
import jp.glory.practice.boot.app.test.tool.WebErrorAssertion
import jp.glory.practice.boot.app.user.command.usecase.CreateUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@WebMvcTest
class UserCreateRouterTest {
    private lateinit var mockMvc: MockMvc
    private val useCase = mockk<CreateUser>()

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcCreator()
            .apply {
                activateUserRoute(
                    createUser = useCase,
                )
            }
            .create()
    }

    @Test
    fun success() {
        val expectedUserId = "generated-user-id"
        val expectedInput = createInput()

        every { useCase.createUser(expectedInput) } returns Ok(expectedUserId)

        val jsonRequest = toJsonValue(expectedInput)

        callApi(jsonRequest).andExpect {
            status { isOk() }
            jsonPath("$.user_id") { value(expectedUserId) }
        }

        verify { useCase.createUser(expectedInput) }
    }

    @Nested
    inner class ValidateError {

        @Nested
        inner class LoginId {
            @Test
            fun isEmpty() {
                val input = createInput(
                    loginId = ""
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.REQUIRED)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            @Test
            fun maxLength() {
                val input = createInput(
                    loginId = "a".repeat(101)
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.MAX_LENGTH)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            @Test
            fun format() {
                val input = createInput(
                    loginId = "@"
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.FORMAT)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            private fun createItemError(type: WebItemErrorType): WebErrors {
                val itemError = WebItemError(
                    name = "login_id",
                    errors = listOf(type)
                )
                return WebErrors(
                    itemErrors = listOf(itemError)
                )
            }
        }

        @Nested
        inner class UserName {
            @Test
            fun isEmpty() {
                val input = createInput(
                    userName = ""
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.REQUIRED)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            @Test
            fun maxLength() {
                val input = createInput(
                    userName = "a".repeat(101)
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.MAX_LENGTH)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            private fun createItemError(type: WebItemErrorType): WebErrors {
                val itemError = WebItemError(
                    name = "user_name",
                    errors = listOf(type)
                )
                return WebErrors(
                    itemErrors = listOf(itemError)
                )
            }
        }

        @Nested
        inner class Password {
            @Test
            fun isEmpty() {
                val input = createInput(
                    password = ""
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.REQUIRED)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            @Test
            fun minLength() {
                val input = createInput(
                    password = "a".repeat(15)
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.MIN_LENGTH)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            @Test
            fun maxLength() {
                val input = createInput(
                    password = "a".repeat(101)
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.MAX_LENGTH)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            @Test
            fun format() {
                val input = createInput(
                    password = "あ".repeat(16)
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError(WebItemErrorType.FORMAT)

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            private fun createItemError(type: WebItemErrorType): WebErrors {
                val itemError = WebItemError(
                    name = "password",
                    errors = listOf(type)
                )
                return WebErrors(
                    itemErrors = listOf(itemError)
                )
            }
        }

        @Nested
        inner class Birthday {
            @Test
            fun isAfter() {
                val input = createInput(
                    birthday = LocalDate.now().plusYears(1)
                )
                val jsonRequest = toJsonValue(input)
                val expected = createItemError()

                WebErrorAssertion(callApi(jsonRequest))
                    .assertBadRequest(expected)
            }

            private fun createItemError(): WebErrors {
                val itemError = WebItemError(
                    name = "birthday",
                    errors = listOf(WebItemErrorType.DATE_IS_AFTER)
                )
                return WebErrors(
                    itemErrors = listOf(itemError)
                )
            }
        }

        @Test
        fun whenMultipleInvalid() {
            val input = createInput(
                loginId = "",
                userName = "",
                password = "",
                birthday = LocalDate.now().plusYears(1)
            )
            val jsonRequest = toJsonValue(input)
            val loginIdError = WebItemError(
                name = "login_id",
                errors = listOf(
                    WebItemErrorType.REQUIRED
                )
            )
            val userNameError = WebItemError(
                name = "user_name",
                errors = listOf(
                    WebItemErrorType.REQUIRED
                )
            )
            val passwordError = WebItemError(
                name = "password",
                errors = listOf(
                    WebItemErrorType.REQUIRED
                )
            )
            val birthdayError = WebItemError(
                name = "birthday",
                errors = listOf(
                    WebItemErrorType.DATE_IS_AFTER
                )
            )
            val expected = WebErrors(
                itemErrors = listOf(loginIdError, userNameError, passwordError, birthdayError)
            )

            WebErrorAssertion(callApi(jsonRequest))
                .assertBadRequest(expected)
        }
    }

    private fun callApi(jsonRequest: String): ResultActionsDsl =
        mockMvc.post("/api/v1/user") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonRequest
        }

    private fun createInput(
        loginId: String = "test-login-id",
        userName: String = "test-user-name",
        password: String = "test-password-123456",
        birthday: LocalDate = LocalDate.now().minusYears(10)
    ): CreateUser.Input = CreateUser.Input(
        loginId = loginId,
        userName = userName,
        password = password,
        birthday = birthday
    )

    private fun toJsonValue(expected: CreateUser.Input): String =
        """
            {
                "login_id": "${expected.loginId}",
                "user_name": "${expected.userName}",
                "password": "${expected.password}",
                "birthday": "${expected.birthday}"
            }
        """.trimIndent()
}
