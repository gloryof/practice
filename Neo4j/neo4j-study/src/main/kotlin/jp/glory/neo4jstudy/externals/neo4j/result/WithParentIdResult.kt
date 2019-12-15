package jp.glory.neo4jstudy.externals.neo4j.result

import org.springframework.data.neo4j.annotation.QueryResult

/**
 * 部署ノードと親部署IDの結果.
 *
 * @param postId 部署ID
 * @param name 部署名
 * @param parentPostId 親部署ID
 */
@QueryResult
data class WithParentIdResult(
    val postId: Long,
    val name: String,
    val parentPostId: Long)