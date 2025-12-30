package jp.glory.practice.boot.app.user.command.web

import com.github.michaelbull.result.Ok
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.glory.practice.boot.app.TestRouting
import jp.glory.practice.boot.app.user.command.usecase.CreateUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

class UserCreateRouterTest {

    private lateinit var mockMvc: MockMvc

    private val useCase = mockk<CreateUser>()

    private val sut = UserCreateRouter(useCase)

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .routerFunctions(TestRouting.user(sut))
            .build()
    }

    @Test
    fun success() {
        val expectedUserId = "generated-user-id"
        val expectedInput = CreateUser.Input(
            loginId = "test-login-id",
            userName = "test-user-name",
            password = "test-password-123456",
            birthday = LocalDate.now().minusYears(10)
        )

        every { useCase.createUser(expectedInput) } returns Ok(expectedUserId)

        val jsonRequest = """
            {
                "loginId": "${expectedInput.loginId}",
                "userName": "${expectedInput.userName}",
                "password": "${expectedInput.password}",
                "birthday": "${expectedInput.birthday}"
            }
        """.trimIndent()

        mockMvc.post("/api/v1/user") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonRequest
        }.andExpect {
            status { isOk() }
            jsonPath("$.userId") { value(expectedUserId) }
        }

        verify { useCase.createUser(expectedInput) }
    }
}
