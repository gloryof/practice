package jp.glory.neo4jstudy.domain.employee.event

import jp.glory.neo4jstudy.domain.post.model.PostId
import java.time.LocalDate

/**
 * 従業員入社イベント.
 *
 * @param entryAt 入社日
 * @param lastName 姓
 * @param firstName 名
 * @param age 年齢
 * @param postId 所属部署ID
 */
data class EntryEmployeeEvent(
    val entryAt: LocalDate,
    val lastName: String,
    val firstName: String,
    val age: Int,
    val postId: PostId
)