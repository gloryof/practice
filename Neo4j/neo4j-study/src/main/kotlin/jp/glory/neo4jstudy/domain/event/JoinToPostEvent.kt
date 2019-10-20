package jp.glory.neo4jstudy.domain.event

import jp.glory.neo4jstudy.domain.model.EmployeeId
import jp.glory.neo4jstudy.domain.model.PostId

/**
 * 部署に所属するイベント.
 *
 * @param postId 部署ID
 * @param employeeId 従業員ID
 */
class JoinToPostEvent(val postId: PostId, val employeeId: EmployeeId)