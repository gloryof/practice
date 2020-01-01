package jp.glory.neo4jstudy.infra

import jp.glory.neo4jstudy.domain.post.event.AddChildPostEvent
import jp.glory.neo4jstudy.domain.post.event.DeletePostEvent
import jp.glory.neo4jstudy.domain.post.event.SavePostEvent
import jp.glory.neo4jstudy.domain.post.model.PostId
import jp.glory.neo4jstudy.domain.post.repository.PostRepository
import jp.glory.neo4jstudy.externals.neo4j.dao.IdDao
import jp.glory.neo4jstudy.externals.neo4j.dao.PostDao
import org.springframework.stereotype.Repository

/**
 * [PostRepository]のNeo4j実装.
 *
 * @param postDao 部署DAO
 */
@Repository
class PostRepositoryNeo4jImpl(private val postDao: PostDao, private val idDao: IdDao) :
    PostRepository {

    override fun save(event: SavePostEvent): PostId {

        val id: PostId = event.postId ?: generateNewId()

        postDao.merge(id.value, event.name)

        return id
    }

    override fun delete(event: DeletePostEvent) {
        postDao.deleteByPostId(event.postId.value)
    }

    override fun saveAddingChild(event: AddChildPostEvent) {
        postDao.addPostRelation(
            postId = event.parentPostId.value,
            childPostId = event.childPostId.value
        )
    }

    /**
     * 新しい部署IDを発行する.
     *
     * @return 部署ID
     */
    private fun generateNewId(): PostId {

        return PostId(idDao.calculateNextPostId().value)
    }
}