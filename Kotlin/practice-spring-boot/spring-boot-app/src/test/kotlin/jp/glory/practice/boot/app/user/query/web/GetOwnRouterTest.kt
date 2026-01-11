package jp.glory.practice.boot.app.user.query.web

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import io.mockk.every
import io.mockk.mockk
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseErrors
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseSpecErrorType
import jp.glory.practice.boot.app.test.tool.MockMvcCreator
import jp.glory.practice.boot.app.test.tool.WebErrorAssertion
import jp.glory.practice.boot.app.user.query.usecase.GetUser
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import java.time.LocalDate
import java.util.UUID

class GetOwnRouterTest {

    @Test
    fun success() {
        val accessToken = UUID.randomUUID().toString()
        val userId = "test-user-id"
        val userName = "test-user-name"
        val birthday = LocalDate.of(1980, 1, 1)

        val getUser: GetUser = mockk()
        every { getUser.getById(userId) } returns Ok(
            GetUser.Output(
                userId = userId,
                userName = userName,
                birthday = birthday
            )
        )

        val mockMvc = createMockMvc(
            usecase = getUser,
            accessToken = accessToken,
            userId = userId
        )
        callApi(
            mockMvc = mockMvc,
            accessToken = accessToken
        ).andExpect {
            status { isOk() }
            jsonPath("$.user_id") { value(userId) }
            jsonPath("$.user_name") { value(userName) }
            jsonPath("$.birthday") { value(birthday.toString()) }
        }
    }

    @Nested
    inner class Fail {
        @Test
        fun notFoundUser() {
            val accessToken = UUID.randomUUID().toString()
            val userId = "test-user-id"

            val getUser: GetUser = mockk()
            every { getUser.getById(userId) } returns Err(
                UsecaseErrors(
                    specErrors = listOf(UsecaseSpecErrorType.DATA_IS_NOT_FOUND)
                )
            )

            val mockMvc = createMockMvc(
                usecase = getUser,
                accessToken = accessToken,
                userId = userId
            )
            val result = callApi(
                mockMvc = mockMvc,
                accessToken = accessToken
            )

            WebErrorAssertion(result)
                .assertNotFound()
        }

        @Test
        fun unauthorized() {
            val mockMvc = createMockMvc()
            val result = callApi(
                mockMvc = mockMvc,
            )

            WebErrorAssertion(result)
                .assertUnauthorized()
        }

    }

    private fun createMockMvc(
        usecase: GetUser = mockk(),
        accessToken: String = "",
        userId: String = ""
    ): MockMvc =
        MockMvcCreator()
            .apply {
                if (accessToken.isNotEmpty() && userId.isNotEmpty()) {
                    activateSuccessAuthenticate(
                        accessToken = accessToken,
                        userId = userId
                    )
                } else {
                    activateFailAuthenticate()
                }
                activateUserRoute(
                    getUser = usecase,
                )
            }
            .create()

    private fun callApi(
        mockMvc: MockMvc,
        accessToken: String = ""
    ): ResultActionsDsl =
        mockMvc.get("/api/v1/user") {
            contentType = MediaType.APPLICATION_JSON
            if (accessToken.isNotEmpty()) {
                header("Authorization", "Bearer $accessToken")
            }
        }
}
