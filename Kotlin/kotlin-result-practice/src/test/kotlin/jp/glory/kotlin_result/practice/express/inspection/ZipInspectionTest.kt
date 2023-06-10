package jp.glory.kotlin_result.practice.express.inspection

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.UnwrapException
import jp.glory.kotlin_result.practice.express.model.ErrorInfo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ZipInspectionTest {
    @Test
    fun success() {
        val actual = ZipInspection().success()
        assertEquals(Ok(3), actual)
    }
    @Test
    fun fail1() {
        val actual = ZipInspection().fail1()
        assertEquals(Err(ErrorInfo.DIV_BY_ZERO), actual)
    }
    @Test
    fun fail2() {
        assertThrows<UnwrapException> {
            ZipInspection().fail2()
        }
    }
}