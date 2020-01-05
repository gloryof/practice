package jp.glory.neo4jstudy.domain.post.event

import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.post.model.PostId
import java.time.LocalDate

/**
 * 退任イベント.
 *
 * @param leaveAt 退任日
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
class LeaveFromPostEvent(val leaveAt:LocalDate, val postId: PostId, val employeeId: EmployeeId)