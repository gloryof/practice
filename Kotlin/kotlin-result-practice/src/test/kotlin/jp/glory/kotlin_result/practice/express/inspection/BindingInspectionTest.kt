package jp.glory.kotlin_result.practice.express.inspection

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import jp.glory.kotlin_result.practice.express.model.ErrorInfo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BindingInspectionTest {
    @Nested
    inner class SuccessPattern1 {
        private val answer = -1
        @Test
        fun pattern1() {
            val actual = BindingInspection.SuccessPattern1().pattern1()
            assertEquals(Ok(answer), actual)
        }
        @Test
        fun pattern2() {
            val actual = BindingInspection.SuccessPattern1().pattern2()
            assertEquals(Ok(answer), actual)
        }
        @Test
        fun pattern3() {
            val actual = BindingInspection.SuccessPattern1().pattern3()
            assertEquals(Ok(answer), actual)
        }
    }

    @Nested
    inner class SuccessPattern2 {
        private val answer = 9
        @Test
        fun pattern1() {
            val actual = BindingInspection.SuccessPattern2().pattern1()
            assertEquals(Ok(answer), actual)
        }
        @Test
        fun pattern2() {
            val actual = BindingInspection.SuccessPattern2().pattern2()
            assertEquals(Ok(answer), actual)
        }
    }

    @Nested
    inner class FailPattern1 {
        @Test
        fun pattern1() {
            val actual = BindingInspection.FailedPattern1().failPattern1()
            assertEquals(Err(ErrorInfo.DIV_BY_ZERO), actual)
        }
    }
}