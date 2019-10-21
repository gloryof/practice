package jp.glory.neo4jstudy.web.request

/**
 * 所属リクエスト.
 *
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
data class JoinRequest(val postId: Long, val employeeId: Long)