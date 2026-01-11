package jp.glory.practice.boot.app.test.tool

import jp.glory.practice.boot.app.base.common.web.exception.ErrorDetail
import jp.glory.practice.boot.app.base.common.web.exception.ErrorResponse
import jp.glory.practice.boot.app.base.common.web.exception.WebErrors
import jp.glory.practice.boot.app.base.common.web.exception.WebSpecErrorType
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActionsDsl
import kotlin.test.assertEquals

class WebErrorAssertion(
    private val dsl: ResultActionsDsl,
) {
    val objectMapper = JsonMapperBuilderCreator.create().build()

    fun assertBadRequest(expected: WebErrors) {
        dsl.andExpect {
            status { isBadRequest() }
        }.andReturn().apply {
            expectError(
                expected = expected,
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    fun assertNotFound() {
        val expected = WebErrors(
            specErrors = listOf(WebSpecErrorType.DATA_IS_NOT_FOUND)
        )
        dsl.andExpect {
            status { isNotFound() }
        }.andReturn().apply {
            expectError(
                expected = expected,
                status = HttpStatus.NOT_FOUND
            )
        }
    }

    fun assertUnauthorized() {
        val expected = WebErrors(
            specErrors = listOf(WebSpecErrorType.UNAUTHORIZED)
        )
        dsl.andExpect {
            status { isUnauthorized() }
        }.andReturn().apply {
            expectError(
                expected = expected,
                status = HttpStatus.UNAUTHORIZED
            )
        }
    }

    private fun MvcResult.expectError(
        expected: WebErrors,
        status: HttpStatus
    ) {
        val body = this.response.contentAsString
        val actual = objectMapper.readValue(body, ErrorResponse::class.java)

        assertEquals(status.value(), actual.status)

        val errors: List<ErrorDetail> = actual.errors

        if (expected.specErrors.isNotEmpty()) {
            val expectedCodes = expected.specErrors.map { it.name }
            val specErrors = errors.first { it.name == "" }

            assertEquals(expectedCodes, specErrors.errorTypes)
        }

        expected.itemErrors.forEach { itemError ->
            val expectedCodes = itemError.errors.map { it.name }
            val specErrors = errors.first { it.name == itemError.name }

            assertEquals(expectedCodes, specErrors.errorTypes)
        }
    }
}
