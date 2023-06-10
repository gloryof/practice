package jp.glory.kotlin_result.practice.express.inspection


import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import jp.glory.kotlin_result.practice.express.model.ErrorInfo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class OrIInspectionTest {
    @Nested
    inner class Or {
        @Test
        fun pattern1() {
            val actual = OrInspection.Or().pattern1()
            assertEquals(Ok(1), actual)
        }
        @Test
        fun pattern2() {
            val actual = OrInspection.Or().pattern2()
            assertEquals(Ok(2), actual)
        }
        @Test
        fun pattern3() {
            val actual = OrInspection.Or().pattern3()
            assertEquals(Err(ErrorInfo.DIV_BY_ZERO), actual)
        }
    }

    @Nested
    inner class OrElse {
        @Test
        fun pattern1() {
            val actual = OrInspection.OrElse().pattern1()
            assertEquals(Ok(1), actual)
        }
        @Test
        fun pattern2() {
            val actual = OrInspection.OrElse().pattern2()
            assertEquals(Ok(2), actual)
        }
        @Test
        fun pattern3() {
            val actual = OrInspection.OrElse().pattern3()
            assertEquals(Err(ErrorInfo.DIV_BY_ZERO), actual)
        }
    }

    @Nested
    inner class Recover {
        @Test
        fun success() {
            val actual = OrInspection.Recover().success()
            assertEquals(Ok(1), actual)
        }
        @Test
        fun fail() {
            val actual = OrInspection.Recover().fail()
            assertEquals(Ok(0), actual)
        }
    }

    @Nested
    inner class RecoverIf {
        @Test
        fun success() {
            val actual = OrInspection.RecoverIf().success()
            assertEquals(Ok(1), actual)
        }
        @Test
        fun fail() {
            val actual = OrInspection.RecoverIf().fail()
            assertEquals(Ok(0), actual)
        }
    }

}