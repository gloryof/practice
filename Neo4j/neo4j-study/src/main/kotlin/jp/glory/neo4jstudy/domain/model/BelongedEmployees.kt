package jp.glory.neo4jstudy.domain.model

/**
 * 所属従業員.
 *
 * @param employees 従業員リスト
 */
class BelongedEmployees(val employees: MutableList<Employee> = mutableListOf()) {
    /**
     * 所属する.
     *
     * @param employee 従業員
     */
    fun join(employee: Employee) = employees.add(employee)
}