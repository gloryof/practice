package jp.glory.practice.boot.app.auth.command.infra.event

import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import jp.glory.practice.boot.app.auth.command.domain.event.TokenIssued
import jp.glory.practice.boot.app.auth.command.domain.model.AuthToken
import jp.glory.practice.boot.app.auth.data.TokenDao
import jp.glory.practice.boot.app.auth.data.TokenRecord
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import org.junit.jupiter.api.Test
import java.time.Clock
import kotlin.test.assertEquals

class AuthEventHandlerImplTest {
    @Test
    fun success() {
        val event = TokenIssued(
            userId = UserIdGenerator().issueNewId(),
            token = AuthToken.Companion.issue(Clock.systemDefaultZone())
        )
        val tokenDao: TokenDao = mockk()
        val tokenSlot: CapturingSlot<TokenRecord> = slot()
        every { tokenDao.insert(capture(tokenSlot)) } just runs

        val sut = createSut(tokenDao)
        sut.handleTokenIssued(event)

        val actual = tokenSlot.captured
        assertEquals(event.userId.value, actual.userId)
        assertEquals(event.token.value, actual.token)
        assertEquals(event.token.expiresAt, actual.expiredAt)
    }

    private fun createSut(tokenDao: TokenDao): AuthEventHandlerImpl =
        AuthEventHandlerImpl(
            tokenDao = tokenDao
        )
}
