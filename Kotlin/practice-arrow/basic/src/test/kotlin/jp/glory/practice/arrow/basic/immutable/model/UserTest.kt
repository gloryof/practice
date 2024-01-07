package jp.glory.practice.arrow.basic.immutable.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UserTest {

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

            val after = before.changeProfile(
                    userName = UserName(expectedName),
                    birthday = Birthday(expectedBirthday)
                )

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