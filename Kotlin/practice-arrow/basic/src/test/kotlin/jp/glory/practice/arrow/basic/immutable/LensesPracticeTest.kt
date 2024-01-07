package jp.glory.practice.arrow.basic.immutable

import jp.glory.practice.arrow.basic.immutable.model.Birthday
import jp.glory.practice.arrow.basic.immutable.model.User
import jp.glory.practice.arrow.basic.immutable.model.UserId
import jp.glory.practice.arrow.basic.immutable.model.UserName
import jp.glory.practice.arrow.basic.immutable.model.UserRepositoryImpl
import jp.glory.practice.arrow.basic.immutable.model.UserStatus
import jp.glory.practice.arrow.basic.immutable.model.birthday
import jp.glory.practice.arrow.basic.immutable.model.status
import jp.glory.practice.arrow.basic.immutable.model.userId
import jp.glory.practice.arrow.basic.immutable.model.userName
import jp.glory.practice.arrow.basic.immutable.model.value
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        fun `when not exist user return null`() {
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

    @Nested
    inner class TestRegister {
        @Test
        fun success() {
            val input = LensesPractice.RegisterInput(
                userName = "test-user",
                birthday = LocalDate.of(1986, 12, 16)
            )
            val repository = UserRepositoryImpl()

            val sut = LensesPractice(repository)

            val actual = sut.register(input)

            Assertions.assertTrue(actual.value.isNotBlank())
        }

        @Test
        fun `when invalid input`() {
            val input = LensesPractice.RegisterInput(
                userName = "",
                birthday = LocalDate.of(1986, 12, 16)
            )
            val repository = UserRepositoryImpl()

            val sut = LensesPractice(repository)

            assertThrows<AssertionError> {
                sut.register(input)
            }
        }
    }

    @Nested
    inner class TestChangeProfile {
        @Test
        fun success() {
            val expectedName = "After"
            val expectedBirthday = LocalDate.of(1986, 12, 16)
            val before = User.create(
                userName = "Before",
                birthday = LocalDate.now()
            )

            val repository = UserRepositoryImpl()
                .also { it.save(before) }

            val sut = LensesPractice(repository)

            sut.changeProfile(
                input = LensesPractice.ChangeProfileInput(
                    userId = User.userId.value.get(before),
                    userName = expectedName,
                    birthday = expectedBirthday
                )
            )

            val after = repository.findByUserId(User.userId.get(before))
                ?: fail("Must not null")

            val same = listOf(
                User.userId,
                User.status
            )
            same.forEach {
                Assertions.assertEquals(it.get(before), it.get(after))
            }
            Assertions.assertEquals(expectedName, User.userName.value.get(after))
            Assertions.assertEquals(expectedBirthday, User.birthday.value.get(after))
        }
    }
}