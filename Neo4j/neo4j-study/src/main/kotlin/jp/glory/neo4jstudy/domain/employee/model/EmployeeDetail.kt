package jp.glory.neo4jstudy.domain.employee.model

/**
 * 従業員詳細.
 *
 * @param employee 従業員
 * @param histories 従業員履歴リスト
 */
data class EmployeeDetail(val employee: Employee, val histories: List<EmployeeHistory>)