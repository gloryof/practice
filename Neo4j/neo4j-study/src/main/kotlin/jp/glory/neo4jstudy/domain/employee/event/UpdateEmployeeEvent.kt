package jp.glory.neo4jstudy.domain.employee.event

import jp.glory.neo4jstudy.domain.employee.model.EmployeeId

/**
 * 従業員保存イベント.
 *
 * @param employeeId 従業員ID
 * @param lastName 姓
 * @param firstName 名
 * @param age 年齢
 */
data class UpdateEmployeeEvent(
    val employeeId: EmployeeId,
    val lastName: String,
    val firstName: String,
    val age:Int
)