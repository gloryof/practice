package jp.glory.neo4jstudy.externals.neo4j.node

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

/**
 * 従業員履歴ノード.
 */
@NodeEntity(label = "EmployeeHistory")
sealed class EmployeeHistoryNode

/**
 * 入社履歴ノード.
 *
 * @param historyId 履歴ID
 * @param entryAt 入社日
 * @param postId 部署ID
 * @param postName 部署名
 */
@NodeEntity(label = "EntryHistory")
data class EntryHistoryNode(
    @Id val historyId: Long,
    val entryAt: String,
    val postId: Long,
    val postName: String): EmployeeHistoryNode()

/**
 * 所属履歴ノード.
 *
 * @param historyId 履歴ID
 * @param joinAt 所属日
 * @param postId 部署ID
 * @param postName 部署名
 */
@NodeEntity(label = "JoinHistory")
data class JoinHistoryNode(
    @Id val historyId: Long,
    val joinAt: String,
    val postId: Long,
    val postName: String): EmployeeHistoryNode()

/**
 * 退任履歴ノード.
 *
 * @param historyId 履歴ID
 * @param leaveAt 退任日
 * @param postId 部署ID
 * @param postName 部署名
 */
@NodeEntity(label = "LeaveHistory")
data class LeaveHistoryNode(
    @Id val historyId: Long,
    val leaveAt: String,
    val postId: Long,
    val postName: String): EmployeeHistoryNode()


/**
 * 退職履歴ノード.
 *
 * @param historyId 履歴ID
 * @param retireAt 退職日
 */
@NodeEntity(label = "RetireHistory")
data class RetireHistoryNode(
    @Id val historyId: Long,
    val retireAt: String): EmployeeHistoryNode()