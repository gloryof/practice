package jp.glory.kotlin_result.practice.express.inspection


import com.github.michaelbull.result.UnwrapException
import jp.glory.kotlin_result.practice.express.model.ErrorInfo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class UnwrapInspectionTest {
    @Nested
    inner class Unwrap {
        @Test
        fun success() {
            val actual = UnwrapInspection.Unwrap().success()
            assertEquals(1, actual)
        }
        @Test
        fun fail() {
            assertThrows<UnwrapException> {
                UnwrapInspection.Unwrap().fail()
            }
        }
    }

    @Nested
    inner class Expect {
        @Test
        fun success() {
            val actual = UnwrapInspection.Expect().success()
            assertEquals(1, actual)
        }
        @Test
        fun fail() {
            assertThrows<UnwrapException> {
                UnwrapInspection.Expect().fail()
            }
        }
    }

    @Nested
    inner class UnwrapError {
        @Test
        fun success() {
            assertThrows<UnwrapException> {
                UnwrapInspection.UnwrapError().success()
            }
        }
        @Test
        fun fail() {
            val actual = UnwrapInspection.UnwrapError().fail()
            assertEquals(ErrorInfo.DIV_BY_ZERO, actual)
        }
    }

    @Nested
    inner class ExpectError {
        @Test
        fun success() {
            assertThrows<UnwrapException> {
                UnwrapInspection.ExpectError().success()
            }
        }
        @Test
        fun fail() {
            val actual = UnwrapInspection.ExpectError().fail()
            assertEquals(ErrorInfo.DIV_BY_ZERO, actual)
        }
    }
}