package jp.glory.practice.boot.app.auth.command.web

import com.github.michaelbull.result.Ok
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.glory.practice.boot.app.auth.command.usecase.IssueToken
import jp.glory.practice.boot.app.base.web.WebErrors
import jp.glory.practice.boot.app.base.web.WebItemError
import jp.glory.practice.boot.app.base.web.WebItemErrorType
import jp.glory.practice.boot.app.test.tool.MockMvcCreator
import jp.glory.practice.boot.app.test.tool.WebErrorAssertion
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post
import java.util.UUID

class LoginRouterTest {
    private lateinit var mockMvc: MockMvc
    private val useCase = mockk<IssueToken>()


    @BeforeEach
    fun setup() {
        mockMvc = MockMvcCreator()
            .apply {
                activateAuthRoute(
                    issueToken = useCase,
                )
            }
            .create()
    }

    @Test
    fun success() {
        val token = UUID.randomUUID().toString()
        val expectedInput: IssueToken.Input = createInput()

        every { useCase.issue(expectedInput) } returns Ok(token)

        val jsonRequest = toJsonValue(expectedInput)

        callApi(jsonRequest).andExpect {
            status { isOk() }
            jsonPath("$.token") { value(token) }
        }

        verify { useCase.issue(expectedInput) }
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
    }


    private fun callApi(jsonRequest: String): ResultActionsDsl =
        mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonRequest
        }

    private fun toJsonValue(input: IssueToken.Input): String =
        """
           {
               "login_id": "${input.loginId}",
               "password": "${input.password}"
           }
        """.trimIndent()

    private fun createInput(
        loginId: String = "test-login-id",
        password: String = "test-password-123456"
    ): IssueToken.Input =
        IssueToken.Input(
            loginId = loginId,
            password = password
        )
}
