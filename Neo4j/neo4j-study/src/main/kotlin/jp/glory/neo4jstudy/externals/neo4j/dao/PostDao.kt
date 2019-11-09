package jp.glory.neo4jstudy.externals.neo4j.dao

import jp.glory.neo4jstudy.externals.neo4j.node.PostNode
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository

/**
 * 部署Dao.
 */
interface PostDao: Neo4jRepository<PostNode, Long> {

    /**
     * 部署ノードをマージする.
     *
     * @param node 従業員ノード
     */
    @Query(
        """
            MERGE (p: Post{postId: {postId}})
            SET p.name = {name}
        """
    )
    fun merge(postId: Long, name: String)

    /**
     * 部署IDをキーに削除する.
     *
     * @param postId 部署ID
     */
    @Query("MATCH (p: Post{postId: {postId}}) DELETE p")
    fun deleteByPostId(postId: Long)

    /**
     * 部署のリレーションを登録する.
     *
     * @param postId 部署ID
     * @param childPostId 子部署ID
     */
    @Query("""
        MATCH (p: Post{postId: {postId}}), (c:Post{postId: {childPostId}})
        CREATE (c)-[:BELONG]->(p)
        """)
    fun addPostRelation(postId: Long, childPostId: Long)

    /**
     * 部署と従業員のリレーションを追加する.
     *
     * @param postId 部署ID
     * @param employeeId 従業員ID
     */
    @Query("""
        MATCH (p: Post{postId: {postId}}), (e:Employee{employeeId: {employeeId}})
        CREATE (e)-[:JOIN]->(p)
    """)
    fun addEmployeeRelation(postId: Long, employeeId: Long)

    /**
     * 部署と従業員のリレーションを削除する.
     *
     * @param postId 部署ID
     * @param employeeId 従業員ID
     */
    @Query("""
        MATCH (e:Employee{employeeId: {employeeId}})-[r]->(p: Post{postId: {postId}})
        DELETE r
    """)
    fun deleteEmployeeRelation(postId: Long, employeeId: Long)
}