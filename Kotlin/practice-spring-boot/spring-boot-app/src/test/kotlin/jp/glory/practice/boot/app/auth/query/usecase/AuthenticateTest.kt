package jp.glory.practice.boot.app.auth.query.usecase

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.every
import io.mockk.mockk
import jp.glory.practice.boot.app.auth.data.TokenDao
import jp.glory.practice.boot.app.auth.data.TokenRecord
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseSpecErrorType
import jp.glory.practice.boot.app.test.tool.UsecaseErrorsAssertion
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.OffsetDateTime
import kotlin.test.assertEquals

class AuthenticateTest {
    @Test
    fun success() {
        val token = " test-token"
        val record = TokenRecord(
            userId = "test-user-id",
            token = token,
            expiredAt = OffsetDateTime.now().plusDays(1)
        )

        val tokenDao: TokenDao = mockk()
        every { tokenDao.findByToken(token) } returns record

        val sut = createSut(tokenDao)

        val actual = sut.authenticate(token).get() ?: fail("Unexpected fail")

        assertEquals(record.userId, actual.userId)
    }

    @Test
    fun fail() {
        val token = " test-token"

        val tokenDao: TokenDao = mockk()
        every { tokenDao.findByToken(token) } returns null

        val sut = createSut(tokenDao)

        val actual = sut.authenticate(token).getError() ?: fail("Unexpected success")
        UsecaseErrorsAssertion(
            specErrors = setOf(UsecaseSpecErrorType.UNAUTHORIZED),
            usecaseErrors = actual
        )
            .assertAll()
    }

    private fun createSut(
        tokenDao: TokenDao = mockk()
    ): Authenticate =
        Authenticate(
            tokenDao = tokenDao
        )
}
