package jp.glory.practice.boot.app.user.query.usecase

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.every
import io.mockk.mockk
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseSpecErrorType
import jp.glory.practice.boot.app.test.tool.UsecaseErrorsAssertion
import jp.glory.practice.boot.app.user.data.UserDao
import jp.glory.practice.boot.app.user.data.UserRecord
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate
import kotlin.test.assertEquals

class GetUserTest {

    @Nested
    inner class TestGetById {
        @Test
        fun success() {
            val expected = GetUser.Output(
                userId = "test-user-id",
                userName = "test-user-name",
                birthday = LocalDate.of(1980, 12, 1)
            )

            val dao: UserDao = mockk()
            val record = UserRecord(
                userId = expected.userId,
                userName = expected.userName,
                birthday = expected.birthday
            )
            every { dao.findById(expected.userId) } returns record

            val sut = createSut(dao)

            val actual = sut.getById(expected.userId).get() ?: fail("User is return")

            assertEquals(expected.userId, actual.userId)
            assertEquals(expected.userName, actual.userName)
            assertEquals(expected.birthday, actual.birthday)
        }

        @Test
        fun notReturn() {
            val userId = "test-user-id"
            val dao: UserDao = mockk()
            every { dao.findById(userId) } returns null

            val sut = createSut(dao)

            val actual = sut.getById(userId).getError() ?: fail("User is return")

            val assertion = UsecaseErrorsAssertion(
                specErrors = setOf(UsecaseSpecErrorType.DATA_IS_NOT_FOUND),
                usecaseErrors = actual
            )
            assertion.assertAll()
        }
    }

    private fun createSut(
        dao: UserDao = mockk()
    ): GetUser =
        GetUser(
            dao = dao
        )
}
