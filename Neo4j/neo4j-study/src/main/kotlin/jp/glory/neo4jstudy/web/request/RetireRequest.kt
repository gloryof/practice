package jp.glory.neo4jstudy.web.request

import java.time.LocalDate

/**
 * 退職リクエスト.
 *
 * @param retireAt 退職日
 * @param employeeId 従業員ID
 */
data class RetireRequest(
    var retireAt: LocalDate = LocalDate.MAX,
    var employeeId: Long = 0)