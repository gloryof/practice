package jp.glory.neo4jstudy.externals.neo4j.node

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

/**
 * 部署ノード.
 */
@NodeEntity(label = "Post")
data class PostNode(
    @Id val postId: Long,
    val name: String)