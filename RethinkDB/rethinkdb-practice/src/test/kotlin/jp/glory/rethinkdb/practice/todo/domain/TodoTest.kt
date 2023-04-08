package jp.glory.rethinkdb.practice.todo.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

internal class TodoTest {
    @Nested
    inner class TestGetStatus {
        @Nested
        inner class UnStarted {
            @Test
            fun `should return UnStarted that has not started yet`() {
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now().plusDays(1),
                    started = false,
                    finished = false
                )
                assertEquals(TodoStatus.UnStarted, todo.getStatus())
            }

            @Test
            fun `should return UnStarted that has been started and deadline is same date`() {
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now(),
                    started = false,
                    finished = false
                )
                assertEquals(TodoStatus.UnStarted, todo.getStatus())
            }
        }

        @Nested
        inner class Overdue {
            @Test
            fun `should return Overdue that has passed its deadline`() {
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now().minusDays(1),
                    started = false,
                    finished = false
                )
                assertEquals(TodoStatus.Overdue, todo.getStatus())
            }
        }

        @Nested
        inner class Started {
            @Test
            fun `should return Started that has been started`() {
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now().plusDays(1),
                    started = true,
                    finished = false
                )
                assertEquals(TodoStatus.Started, todo.getStatus())
            }

            @Test
            fun `should return Started that has been started and deadline is same date`() {
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now(),
                    started = true,
                    finished = false
                )
                assertEquals(TodoStatus.Started, todo.getStatus())
            }
        }

        @Nested
        inner class Finished {
            @Test
            fun `should return Finished that has been finished`() {
                val todo = Todo(
                    id = "1",
                    title = "Test Todo",
                    deadLine = LocalDate.now().plusDays(1),
                    started = false,
                    finished = true
                )
                assertEquals(TodoStatus.Finished, todo.getStatus())
            }
        }
    }

    @Nested
    inner class TestGetDaysLeft {
        @Test
        fun `test getDaysLeft when deadline is in the future`() {
            val todo = Todo(
                id = "1",
                title = "Test Todo",
                deadLine = LocalDate.of(2023, 4, 15),
                started = false,
                finished = false
            )
            assertEquals(7, todo.getDaysLeft())
        }

        @Test
        fun `test getDaysLeft when deadline is today`() {
            val todo = Todo(
                id = "1",
                title = "Test Todo",
                deadLine = LocalDate.now(),
                started = false,
                finished = false
            )
            assertEquals(0, todo.getDaysLeft())
        }

        @Test
        fun `test getDaysLeft when deadline is in the past`() {
            val todo = Todo(
                id = "1",
                title = "Test Todo",
                deadLine = LocalDate.of(2021, 12, 31),
                started = false,
                finished = false
            )
            assertEquals(-463, todo.getDaysLeft())
        }
    }

    @Nested
    inner class TestChange {
        @Test
        fun `change deadline to the future should update deadline`() {
            val todo = Todo(
                id = "1",
                title = "Finish project",
                deadLine = LocalDate.now().plusDays(7),
                started = false,
                finished = false
            )
            val newTitle = "Finish project by next week"
            val newDeadLine = LocalDate.now().plusDays(14)
            todo.change(newTitle, newDeadLine)
            assertEquals(newTitle, todo.getTitle())
            assertEquals(newDeadLine, todo.getDeadLine())
        }

        @Test
        fun `change deadline to the past should throw exception`() {
            val todo = Todo(
                id = "1",
                title = "Finish project",
                deadLine = LocalDate.now().plusDays(7),
                started = false,
                finished = false
            )
            val newTitle = "Finish project yesterday"
            val newDeadLine = LocalDate.now().minusDays(1)
            assertThrows<IllegalArgumentException> {
                todo.change(newTitle, newDeadLine)
            }
            assertEquals("Finish project", todo.getTitle())
            assertEquals(LocalDate.now().plusDays(7), todo.getDeadLine())
        }
    }
}