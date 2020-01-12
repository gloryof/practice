package jp.glory.neo4jstudy.domain.post.event

import jp.glory.neo4jstudy.domain.post.model.PostId

/**
 * 部署削除イベント.
 *
 * @param postIds 部署IDリスト
 */
class DeletePostEvent(val postIds: List<PostId>)