package jp.glory.rethinkdb.practice.todo.domain.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


// This para of code is generated from ChatGPT by OpenAI(https://openai.com/)
internal class DeadLineServiceTest {

    @Nested
    inner class TestIsOverDue {

        @Test
        fun `isOverDue with past date should return true`() {
            val now = Instant.parse("2022-01-01T00:00:00Z")
            val clock = Clock.fixed(now, ZoneId.of("UTC"))
            val pastDate = LocalDate.parse("2021-12-31")

            assertTrue(DeadLineService.isOverDue(pastDate, clock))
        }

        @Test
        fun `isOverDue with future date should return false`() {
            val now = Instant.parse("2022-01-01T00:00:00Z")
            val clock = Clock.fixed(now, ZoneId.of("UTC"))
            val futureDate = LocalDate.parse("2022-01-02")

            assertFalse(DeadLineService.isOverDue(futureDate, clock))
        }

        @Test
        fun `isOverDue with today's date should return false`() {
            val now = Instant.parse("2022-01-01T00:00:00Z")
            val clock = Clock.fixed(now, ZoneId.of("UTC"))

            assertFalse(DeadLineService.isOverDue(LocalDate.now(clock), clock))
        }
    }
}