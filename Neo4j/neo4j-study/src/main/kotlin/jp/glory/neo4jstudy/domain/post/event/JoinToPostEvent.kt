package jp.glory.neo4jstudy.domain.post.event

import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.post.model.PostId
import java.time.LocalDate

/**
 * 部署に所属するイベント.
 *
 * @param joinAt 所属日
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
class JoinToPostEvent(val joinAt:LocalDate, val postId: PostId, val employeeId: EmployeeId)