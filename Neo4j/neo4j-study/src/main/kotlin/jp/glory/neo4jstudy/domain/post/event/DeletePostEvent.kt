package jp.glory.neo4jstudy.domain.post.event

import jp.glory.neo4jstudy.domain.post.model.PostId

/**
 * 部署削除イベント.
 *
 * @param postId 部署ID
 */
class DeletePostEvent(val postId: PostId)