package jp.glory.neo4jstudy.domain.organization.model

import jp.glory.neo4jstudy.domain.employee.model.Employee

/**
 * 未所属従業員.
 *
 * @param employees 従業員リスト
 */
class NonBelonggingEmployees(val employees: List<Employee>)