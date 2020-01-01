package jp.glory.neo4jstudy.domain.post.event

import jp.glory.neo4jstudy.domain.post.model.PostId

/**
 * 子部署追加イベント.
 *
 * @param parentPostId 親部署ID
 * @param childPostId 子部署ID
 */
class AddChildPostEvent(val parentPostId: PostId, val childPostId: PostId)