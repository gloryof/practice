package jp.glory.neo4jstudy.domain.employee.event

import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import java.time.LocalDate

/**
 * 従業員退職イベント.
 *
 * @param employeeId 従業員ID
 */
class RetireEmployeeEvent(
    val retireAt: LocalDate,
    val employeeId: EmployeeId)