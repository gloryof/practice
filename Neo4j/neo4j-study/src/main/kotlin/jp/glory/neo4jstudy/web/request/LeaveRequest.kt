package jp.glory.neo4jstudy.web.request

/**
 * 所属から外れるリクエスト.
 *
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
data class LeaveRequest(
    var postId: Long = 0,
    var employeeId: Long = 0)