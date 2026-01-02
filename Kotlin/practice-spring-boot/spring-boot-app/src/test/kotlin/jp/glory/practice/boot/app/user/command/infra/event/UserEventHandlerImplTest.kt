package jp.glory.practice.boot.app.user.command.infra.event

import com.github.michaelbull.result.get
import io.mockk.CapturingSlot
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import jp.glory.practice.boot.app.auth.data.AuthDao
import jp.glory.practice.boot.app.auth.data.AuthRecord
import jp.glory.practice.boot.app.user.command.domain.event.UserCreated
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import jp.glory.practice.boot.app.user.data.UserDao
import jp.glory.practice.boot.app.user.data.UserRecord
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate
import kotlin.test.assertEquals

class UserEventHandlerImplTest {

    @Test
    fun success() {
        val userDao: UserDao = mockk()
        val userSlot: CapturingSlot<UserRecord> = slot()
        every {
            userDao.insert(capture(userSlot))
        } just Runs

        val authDao: AuthDao = mockk()
        val authSlot: CapturingSlot<AuthRecord> = slot()
        every {
            authDao.insert(capture(authSlot))
        } just Runs

        val sut = createSut(
            userDao = userDao,
            authDao = authDao
        )

        val today = LocalDate.now()
        val created: UserCreated = UserCreated.create(
            inputLoginId = "test-login-id",
            inputUserName = "test-user-name",
            inputPassword = "test-password-123456",
            inputBirthday = today.minusYears(20),
            today = today,
            userIdGenerator = UserIdGenerator()
        ).get() ?: fail("Invalid ")

        sut.handleUserCreated(created)

        val actualUser: UserRecord = userSlot.captured
        assertEquals(created.userId.value, actualUser.userId)
        assertEquals(created.userName.value, actualUser.userName)
        assertEquals(created.birthday.value, actualUser.birthday)

        val actualAuth: AuthRecord = authSlot.captured
        assertEquals(created.loginId.value, actualAuth.loginId)
        assertEquals(created.userId.value, actualAuth.userId)
        assertEquals(created.password.value, actualAuth.password)
    }

    private fun createSut(
        userDao: UserDao,
        authDao: AuthDao
    ): UserEventHandlerImpl =
        UserEventHandlerImpl(
            userDao = userDao,
            authDao = authDao
        )
}
