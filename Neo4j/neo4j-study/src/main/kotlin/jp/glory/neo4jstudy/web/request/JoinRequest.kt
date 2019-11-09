package jp.glory.neo4jstudy.web.request

/**
 * 所属リクエスト.
 *
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
data class JoinRequest(
    var postId: Long = 0,
    var employeeId: Long = 0)