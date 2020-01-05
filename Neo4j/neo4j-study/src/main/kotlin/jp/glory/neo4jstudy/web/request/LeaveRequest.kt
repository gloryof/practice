package jp.glory.neo4jstudy.web.request

import java.time.LocalDate

/**
 * 所属から外れるリクエスト.
 *
 * @param leaveAt 退任日
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
data class LeaveRequest(
    var leaveAt: LocalDate = LocalDate.MAX,
    var postId: Long = 0,
    var employeeId: Long = 0)