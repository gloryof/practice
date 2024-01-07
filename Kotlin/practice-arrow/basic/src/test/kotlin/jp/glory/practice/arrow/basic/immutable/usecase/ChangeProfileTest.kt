package jp.glory.practice.arrow.basic.immutable.usecase

import jp.glory.practice.arrow.basic.immutable.model.User
import jp.glory.practice.arrow.basic.immutable.model.UserRepositoryImpl
import jp.glory.practice.arrow.basic.immutable.model.birthday
import jp.glory.practice.arrow.basic.immutable.model.status
import jp.glory.practice.arrow.basic.immutable.model.userId
import jp.glory.practice.arrow.basic.immutable.model.userName
import jp.glory.practice.arrow.basic.immutable.model.value
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate

class ChangeProfileTest {
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

            val sut = ChangeProfile(repository)

            sut.change(
                input = ChangeProfile.Input(
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