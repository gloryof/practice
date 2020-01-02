package jp.glory.neo4jstudy.domain.employee.model

import jp.glory.neo4jstudy.domain.post.model.Post
import jp.glory.neo4jstudy.domain.post.model.PostId
import java.time.LocalDate

/**
 * 従業員履歴.
 */
sealed class EmployeeHistory

/**
 * 入社履歴.
 *
 * @param entryAt 入社日
 * @param post 所属部署
 */
data class EntryHistory(
    val entryAt: LocalDate,
    var post: Post) : EmployeeHistory()

/**
 * 部署所属履歴.
 *
 * @param joinAt 所属日
 * @param post 所属部署
 */
data class JoinToPostHistory(
    val joinAt: LocalDate,
    val post: Post
) : EmployeeHistory()

/**
 * 部署から離れた履歴.
 *
 * @param leaveAt 離れた日付
 * @param post 所属
 */
data class LeaveFromPostHistory(
    val leaveAt: LocalDate,
    val post: Post
) : EmployeeHistory()

/**
 * 退職履歴.
 *
 * @param retireAt 退職日
 */
data class RetireHistory(
    val retireAt: LocalDate
)