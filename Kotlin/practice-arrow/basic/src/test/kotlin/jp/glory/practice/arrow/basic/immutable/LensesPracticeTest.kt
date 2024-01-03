package jp.glory.practice.arrow.basic.immutable

import jp.glory.practice.arrow.basic.immutable.model.Birthday
import jp.glory.practice.arrow.basic.immutable.model.User
import jp.glory.practice.arrow.basic.immutable.model.UserId
import jp.glory.practice.arrow.basic.immutable.model.UserName
import jp.glory.practice.arrow.basic.immutable.model.UserRepositoryImpl
import jp.glory.practice.arrow.basic.immutable.model.UserStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate
import java.util.UUID

class LensesPracticeTest {
    @Nested
    inner class TestFindById {
        @Test
        fun `when exist user return data`() {
            val expectedUser = User(
                userId = UserId(UUID.randomUUID().toString()),
                userName = UserName("test-user"),
                birthday = Birthday(LocalDate.of(1986, 12, 16)),
                status = UserStatus.Active

            )
            val repository = UserRepositoryImpl()
                .also { it.save(expectedUser) }

            val actual = LensesPractice(repository)
                .getById(expectedUser.userId.value)
                ?: fail("Must not null")

            Assertions.assertEquals(expectedUser.userId.value, actual.userId)
            Assertions.assertEquals(expectedUser.userName.value, actual.userName)
            Assertions.assertEquals(expectedUser.birthday.value, actual.birthday)
            Assertions.assertEquals(LensesPractice.UserResult.Status.Active, actual.status)
        }
        @Test
        fun `when nont exist user return null`() {
            val expectedUser = User(
                userId = UserId(UUID.randomUUID().toString()),
                userName = UserName("test-user"),
                birthday = Birthday(LocalDate.of(1986, 12, 16)),
                status = UserStatus.Active

            )
            val repository = UserRepositoryImpl()

            val actual = LensesPractice(repository)
                .getById(expectedUser.userId.value)

            Assertions.assertNull(actual)
        }
    }
}