package jp.glory.neo4jstudy.externals.neo4j.result

import jp.glory.neo4jstudy.externals.neo4j.node.EmployeeNode
import org.springframework.data.neo4j.annotation.QueryResult

/**
 * 部署ノードと親部署IDの結果.
 *
 * @param employee 従業員ノード
 * @param joinPostId 所属部署ID
 */
@QueryResult
data class EmployeeResult(
    val employee: EmployeeNode,
    val joinPostId: Long)