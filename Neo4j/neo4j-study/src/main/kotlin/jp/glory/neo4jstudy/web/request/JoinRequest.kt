package jp.glory.neo4jstudy.web.request

import java.time.LocalDate

/**
 * 所属リクエスト.
 *
 * @param joinAt 所属日
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
data class JoinRequest(
    var joinAt: LocalDate = LocalDate.MAX,
    var postId: Long = 0,
    var employeeId: Long = 0)