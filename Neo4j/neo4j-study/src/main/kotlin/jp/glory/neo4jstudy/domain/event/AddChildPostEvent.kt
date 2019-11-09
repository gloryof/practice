package jp.glory.neo4jstudy.domain.event

import jp.glory.neo4jstudy.domain.model.PostId

/**
 * 子部署追加イベント.
 *
 * @param parentPostId 親部署ID
 * @param childPostEvent 子部署ID
 */
class AddChildPostEvent(val parentPostId: PostId, val childPostId: PostId)