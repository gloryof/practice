package jp.glory.neo4jstudy.web.response

import jp.glory.neo4jstudy.usecase.SearchEmployee

/**
 * 従業員詳細レスポンス.
 *
 * @param result 従業員詳細の結果
 */
class EmployeeDetailResponse (private val result: SearchEmployee.EmployeeDetailResult) {

    /**
     * 従業員ID.
     */
    val employeeId: Long = result.employeeId

    /**
     * 姓.
     */
    val lastName: String = result.lastName

    /**
     * 名.
     */
    val firstName: String = result.firstName

    /**
     * 年齢.
     */
    val age: Int = result.age

    /**
     * 従業員履歴.
     */
    val histories: List<EmployeeHistoryDetailResponse> =
        result.histories.map { EmployeeHistoryDetailResponse(it) }
}