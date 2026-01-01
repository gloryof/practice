package jp.glory.practice.boot.app.test.tool

import jp.glory.practice.boot.app.base.web.WebErrors
import org.hamcrest.Matchers.containsInAnyOrder
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.ResultActionsDsl

class WebErrorAssertion(
    private val dsl: ResultActionsDsl,
) {
    fun assertBadRequest(expected: WebErrors) {
        dsl.andExpect {
            status { isBadRequest() }
            expectError(expected)
        }
    }

    private fun MockMvcResultMatchersDsl.expectError(expected: WebErrors) {
        content {
            if (expected.specErrors.isNotEmpty()) {
                val expectedCodes = expected.specErrors.map { it.name }

                jsonPath("$.errors[?(@.name == '')].error_types[0].length()") {
                    value(expectedCodes.size)
                }

                jsonPath("$.errors[?(@.name == '')].error_types[0]") {
                    value(containsInAnyOrder(*expectedCodes.toTypedArray()))
                }
            }

            expected.itemErrors.forEach { itemError ->
                val expectedCodes = itemError.errors.map { it.name }

                jsonPath("$.errors[?(@.name == '${itemError.name}')].error_types.length()") {
                    value(expectedCodes.size)
                }

                jsonPath("$.errors[?(@.name == '${itemError.name}')].error_types[0]") {
                    value(containsInAnyOrder(*expectedCodes.toTypedArray()))
                }
            }
        }
    }
}
