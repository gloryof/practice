package jp.glory.neo4jstudy.externals.neo4j.dao

import jp.glory.neo4jstudy.externals.neo4j.node.PostNode
import jp.glory.neo4jstudy.externals.neo4j.result.WithParentIdResult
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository

/**
 * 部署Dao.
 */
interface PostDao: Neo4jRepository<PostNode, Long> {

    /**
     * 部署IDをキーに部署ノードを取得する.
     *
     * @param postId 部署ID
     * @return 部署ノード
     */
    @Query("""
        MATCH (p:Post {postId: {postId}})
        RETURN p
    """)
    fun findByPostId(postId: Long): PostNode

    /**
     * 部署のルートノードを取得する.
     *
     * @return ルートノードのリスト
     */
    @Query(
        """
            MATCH (p:Post)
            WHERE NOT (p)-[:BELONG]->()
            WITH p AS root
            RETURN root AS post
            ORDER BY root.postId
        """
    )
    fun findRootNode(): List<PostNode>

    /**
     * 対象のルートノードのツリーを取得する.
     *
     * @param postId ルートノードID
     * @return ツリー
     */
    @Query("""
        MATCH (p:Post{postId: {postId}})
        WITH p AS root
        MATCH p=(root)<-[:BELONG*]-(child:Post)
        WITH child.postId AS postId, SIZE(RELATIONSHIPS(p)) AS depth
        ORDER BY depth, child.postId
        RETURN postId
    """)
    fun findTreeIdByRootId(postId: Long): List<Long>

    /**
     * 対象部署と親部署IDを取得する.
     *
     * @param postId 対象部署ID
     * @return 対象部署と親部署ID
     */
    @Query("""
        MATCH (parent:Post)<-[:BELONG]-(child:Post{postId:{postId}})
        RETURN
            child.postId AS postId,
            child.name AS name,
            parent.postId AS parentPostId
    """)
    fun findNodeWithParentId(postId: Long): WithParentIdResult

    /**
     * 部署ノードをマージする.
     *
     * @param postId 部署ID
     * @param name 部署名
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
     * @param postIds 部署IDリスト
     */
    @Query("""
        MATCH (p: Post) 
        WHERE p.postId IN {postIds}
        DETACH DELETE p
    """)
    fun deleteByPostIds(postIds: List<Long>)

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

    /**
     * 親部署IDに紐づく子部署IDのリストを取得する.
     *
     * @param parentId 親部署ID
     * @return 子部署IDリスト
     */
    @Query("""
        MATCH (p:Post {postId: {parentId}})<-[r:BELONG*]-(c:Post)
        WITH p,c,SIZE(r) AS depth
        RETURN c.postId AS postId
        ORDER BY depth
    """)
    fun findChildrenPostId(parentId: Long): List<Long>
}