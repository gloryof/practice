package jp.glory.neo4jstudy.domain.post.event

import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.post.model.PostId

/**
 * 部署から外れるイベント.
 *
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
class LeaveFromPostEvent(val postId: PostId, val employeeId: EmployeeId)