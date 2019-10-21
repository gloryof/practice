package jp.glory.neo4jstudy.usecase

import jp.glory.neo4jstudy.domain.event.DeletePostEvent
import jp.glory.neo4jstudy.domain.event.SavePostEvent
import jp.glory.neo4jstudy.domain.model.PostId
import jp.glory.neo4jstudy.domain.repository.PostRepository
import jp.glory.neo4jstudy.usecase.annotation.Usecase

/***
 * 部署の編集.
 *
 * @param repository 部署リポジトリ
 */
@Usecase
class ModifyPost(private val repository: PostRepository) {

    /**
     * 部署の登録を行う.
     *
     * @param info 登録情報
     */
    fun register(info: RegisterInfo): Long {

        val event = SavePostEvent(
            name =  info.name
        )

        val id: PostId = repository.save(event)

        return id.value
    }

    /**
     * 部署の更新を行う.
     *
     * @param info 登録情報
     */
    fun update(info: UpdateInfo): Long {

        val event = SavePostEvent(
            postId = PostId(info.postId),
            name = info.name
        )

        val id: PostId = repository.save(event)

        return id.value
    }

    /**
     * 部署の削除を行う.
     *
     * @param postId 部署iD
     */
    fun delete(postId: Long) {

        val event = DeletePostEvent(
            postId = PostId(postId)
        )

        repository.delete(event)
    }

    /**
     * 部署の登録情報.
     *
     * @param name 部署名
     */
    class RegisterInfo(val name: String)

    /**
     * 部署の更新情報.
     *
     * @param postId 部署ID
     * @param name 部署名
     */
    class UpdateInfo(val postId: Long, val name: String)
}