package jp.glory.practice.measure.architecture.product.domain.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class MemberTest {

    @Nested
    inner class TestCalculateAge {
        @Test
        fun `Return value1`() {
            val sut = Member(
                id = MemberID("test-member-id"),
                name = MemberName(GivenName("test"), FamilyName("user")),
                birthDay = LocalDate.of(1986, 12, 16)
            )
            val today = LocalDate.of(2023, 12, 15)

            Assertions.assertEquals(36, sut.calculateAge(today))
        }
        @Test
        fun `Return value2`() {
            val sut = Member(
                id = MemberID("test-member-id"),
                name = MemberName(GivenName("test"), FamilyName("user")),
                birthDay = LocalDate.of(1986, 12, 16)
            )
            val today = LocalDate.of(2023, 12, 16)

            Assertions.assertEquals(37, sut.calculateAge(today))
        }
    }
}