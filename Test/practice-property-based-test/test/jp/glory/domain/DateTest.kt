package jp.glory.domain

import io.kotest.core.spec.style.WordSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localDate
import io.kotest.property.forAll
import java.time.LocalDate

class DateTest : WordSpec({
    "When min is 3/1" should {
        val min = LocalDate.of(2020, 3, 1)
        val max = LocalDate.of(2020, 12, 31)
        "generated value is over min" {
            forAll(10_000, Arb.localDate(min, max)) {
                it == min || it.isAfter(min)
            }
        }
    }
})