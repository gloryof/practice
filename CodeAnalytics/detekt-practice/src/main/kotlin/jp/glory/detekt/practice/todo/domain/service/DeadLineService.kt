package jp.glory.detekt.practice.todo.domain.service

import java.time.Clock
import java.time.LocalDate

object DeadLineService {
    fun isOverDue(
        date: LocalDate,
        clock: Clock
    ): Boolean = date.isBefore(LocalDate.now(clock))
}
