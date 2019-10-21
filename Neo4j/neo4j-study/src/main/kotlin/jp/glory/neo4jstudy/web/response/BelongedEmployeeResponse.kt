package jp.glory.neo4jstudy.web.response

import jp.glory.neo4jstudy.usecase.SearchOrganization

/**
 * 所属従業員のレスポンス.
 *
 * @param employee 所属従業員
 */
data class BelongedEmployeeResponse(private val employee: SearchOrganization.BelongedEmployeeDetail) {

    /**
     * 従業員ID.
     */
    val employeeId: Long = employee.employeeId

    /**
     * 姓.
     */
    val lastName: String = employee.lastName

    /**
     * 名.
     */
    val firstName: String = employee.firstName

    /**
     * 年齢.
     */
    val age: Int = employee.age
}