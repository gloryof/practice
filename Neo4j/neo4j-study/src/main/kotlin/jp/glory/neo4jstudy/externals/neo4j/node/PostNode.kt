package jp.glory.neo4jstudy.externals.neo4j.node

import jp.glory.neo4jstudy.domain.model.PostId
import org.neo4j.ogm.annotation.NodeEntity

/**
 * 部署ノード.
 *
 * @param postId 部署ID
 * @param name 部署名
 */
data class PostNode(val postId: Long, val name: String)