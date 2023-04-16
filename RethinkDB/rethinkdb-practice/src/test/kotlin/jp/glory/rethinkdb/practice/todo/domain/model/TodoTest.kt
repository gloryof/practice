package jp.glory.rethinkdb.practice.todo.domain.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.*

// This para of code is generated from ChatGPT by OpenAI(https://openai.com/)
internal class TodoTest {
    @Nested
    inner class TestGetProgressStatus {
        @Nested
        inner class UnStarted {
            @Test
            fun `should return UnStarted that has not started yet`() {
                val clock = getTodayClock()
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now(clock).plusDays(1),
                    started = false,
                    finished = false
                )
                Assertions.assertEquals(ProgressStatus.UnStarted, todo.getProgressStatus())
            }

            @Test
            fun `should return UnStarted that has been started and deadline is same date`() {
                val clock = getTodayClock()
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now(clock),
                    started = false,
                    finished = false
                )
                Assertions.assertEquals(ProgressStatus.UnStarted, todo.getProgressStatus())
            }
        }

        @Nested
        inner class Started {
            @Test
            fun `should return Started that has been started`() {
                val clock = getTodayClock()
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now(clock).plusDays(1),
                    started = true,
                    finished = false
                )
                Assertions.assertEquals(ProgressStatus.Started, todo.getProgressStatus())
            }

            @Test
            fun `should return Started that has been started and deadline is same date`() {
                val clock = getTodayClock()
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now(clock),
                    started = true,
                    finished = false
                )
                Assertions.assertEquals(ProgressStatus.Started, todo.getProgressStatus())
            }
        }

        @Nested
        inner class Finished {
            @Test
            fun `should return Finished that has been finished`() {
                val clock = getTodayClock()
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now(clock).plusDays(1),
                    started = false,
                    finished = true
                )
                Assertions.assertEquals(ProgressStatus.Finished, todo.getProgressStatus())
            }
        }
    }

    @Nested
    inner class TestGetDaysLeft {
        @Test
        fun `test getDaysLeft when deadline is in the future`() {
            val clock = getTodayClock()
            val todo = Todo(
                id = "1",
                title = "Test Todo",
                deadLine = LocalDate.of(2023, 4, 15),
                started = false,
                finished = false
            )
            Assertions.assertEquals(7, todo.getDaysLeft(clock))
        }

        @Test
        fun `test getDaysLeft when deadline is today`() {
            val clock = getTodayClock()
            val todo = Todo(
                id = "1",
                title = "Test Todo",
                deadLine = LocalDate.now(clock),
                started = false,
                finished = false
            )
            Assertions.assertEquals(0, todo.getDaysLeft(clock))
        }

        @Test
        fun `test getDaysLeft when deadline is in the past`() {
            val clock = getTodayClock()
            val todo = Todo(
                id = "1",
                title = "Test Todo",
                deadLine = LocalDate.of(2021, 12, 31),
                started = false,
                finished = false
            )
            Assertions.assertEquals(-463, todo.getDaysLeft(clock))
        }
    }

    @Nested
    inner class TestChange {
        @Test
        fun `change deadline to the future should update deadline`() {
            val clock = getTodayClock()
            val todo = Todo(
                id = "1",
                title = "Finish project",
                deadLine = LocalDate.now(clock).plusDays(7),
                started = false,
                finished = false
            )
            val newTitle = "Finish project by next week"
            val newDeadLine = LocalDate.now(clock).plusDays(14)
            val result = todo.change(newTitle, newDeadLine, clock)

            Assertions.assertEquals(newTitle, result.newTitle)
            Assertions.assertEquals(todo.title, result.oldTile)
            Assertions.assertEquals(newDeadLine, result.newDeadLine)
            Assertions.assertEquals(todo.deadLine, result.oldDeadLine)
        }

        @Test
        fun `change deadline to the past should throw exception`() {
            val clock = getTodayClock()
            val todo = Todo(
                id = "1",
                title = "Finish project",
                deadLine = LocalDate.now(clock).plusDays(7),
                started = false,
                finished = false
            )
            val newTitle = "Finish project yesterday"
            val newDeadLine = LocalDate.now(clock).minusDays(1)
            assertThrows<IllegalArgumentException> {
                todo.change(newTitle, newDeadLine, clock)
            }
            Assertions.assertEquals("Finish project", todo.title)
            Assertions.assertEquals(LocalDate.now(clock).plusDays(7), todo.deadLine)
        }
    }

    @Nested
    inner class TestIsOverDue {
        @Test
        fun `test isOverDue with past deadline`() {
            val todo = Todo(
                id = "1",
                title = "test todo",
                deadLine = LocalDate.of(2022, 1, 1),
                started = false,
                finished = false
            )

            val clock = Clock.fixed(
                LocalDate.of(2022, 1, 2).atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC
            )

            assertTrue(todo.isOverDue(clock))
        }

        @Test
        fun `test isOverDue with future deadline`() {
            val todo = Todo(
                id = "1",
                title = "test todo",
                deadLine = LocalDate.of(2022, 1, 1),
                started = false,
                finished = false
            )

            val clock = Clock.fixed(
                LocalDate.of(2021, 12, 31).atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC
            )

            assertFalse(todo.isOverDue(clock))
        }

        @Test
        fun `test isOverDue with deadline today`() {
            val todo = Todo(
                id = "1",
                title = "test todo",
                deadLine = LocalDate.now(),
                started = false,
                finished = false
            )

            val clock = Clock.fixed(
                LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC
            )

            assertFalse(todo.isOverDue(clock))
        }
    }

    private fun getTodayClock(): Clock =
        ZoneOffset.UTC.
            let {
                Clock.fixed(
                    LocalDateTime.of(
                        LocalDate.of(2023, 4, 8),
                        LocalTime.now(),
                    ).toInstant(it),
                    it
                )
            }
}