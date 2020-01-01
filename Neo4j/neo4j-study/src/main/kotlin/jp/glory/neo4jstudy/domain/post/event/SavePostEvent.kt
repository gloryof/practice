package jp.glory.neo4jstudy.domain.post.event

import jp.glory.neo4jstudy.domain.post.model.PostId


/**
 * 部署保存イベント.
 *
 * @param postId 部署ID
 * @param name 部署名
 */
class SavePostEvent(val postId: PostId? = null, val name: String) {

    /**
     * 新規登録かを判定する.
     * @return 新規登録の場合：true、更新の場合：false
     */
    fun isRegister(): Boolean = (postId == null)
}