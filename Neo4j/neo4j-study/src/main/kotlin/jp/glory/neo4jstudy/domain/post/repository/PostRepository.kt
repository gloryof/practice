package jp.glory.neo4jstudy.domain.post.repository

import jp.glory.neo4jstudy.domain.post.event.AddChildPostEvent
import jp.glory.neo4jstudy.domain.post.event.DeletePostEvent
import jp.glory.neo4jstudy.domain.post.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.post.event.LeaveFromPostEvent
import jp.glory.neo4jstudy.domain.post.event.SavePostEvent
import jp.glory.neo4jstudy.domain.post.model.PostId

/**
 * 部署のリポジトリ.
 */
interface PostRepository {

    /**
     * 部署を保存する.
     *
     * @param event 保存イベント
     * @return 保存された部署のIDを返す
     */
    fun save(event: SavePostEvent): PostId

    /**
     * 部署を削除する.
     *
     * @param event 削除イベント
     */
    fun delete(event: DeletePostEvent)

    /**
     * 子部署の追加を保存する.
     *
     * @param event イベント
     */
    fun saveAddingChild(event: AddChildPostEvent)

    /**
     * 部署への所属を保存する.
     *
     * @param event 保存イベント
     */
    fun saveJoining(event: JoinToPostEvent)

    /**
     * 部署から退任を保存する.
     *
     * @param event イベント
     */
    fun saveLeaving(event: LeaveFromPostEvent)
}