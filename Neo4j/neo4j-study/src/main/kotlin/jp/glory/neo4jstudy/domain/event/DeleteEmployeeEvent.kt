package jp.glory.neo4jstudy.domain.event

import jp.glory.neo4jstudy.domain.model.EmployeeId

/**
 * 従業員削除イベント.
 *
 * @param employeeId 従業員ID
 */
class DeleteEmployeeEvent(val employeeId: EmployeeId)