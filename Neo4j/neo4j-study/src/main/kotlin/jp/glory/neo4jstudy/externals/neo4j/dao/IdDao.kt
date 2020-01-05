package jp.glory.neo4jstudy.externals.neo4j.dao

import jp.glory.neo4jstudy.externals.neo4j.node.IdNode
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

/**
 * IDのDao.
 */
@Repository
interface IdDao: Neo4jRepository<IdNode, Long> {

    /**
     * 新しい従業員IDを計算する.
     *
     * @return IDノード
     */
    @Query("""
        MERGE (i: Id{ name: "employee" })
        ON CREATE SET i.value = 1
        ON MATCH SET i.value = i.value + 1
        return i
    """)
    fun calculateNextEmployeeId(): IdNode

    /**
     * 新しい部署IDを計算する.
     *
     * @return IDノード
     */
    @Query("""
        MERGE (i: Id{ name: "post" })
        ON CREATE SET i.value = 1
        ON MATCH SET i.value = i.value + 1
        return i
    """)
    fun calculateNextPostId(): IdNode

    /**
     * 新しい履歴IDを計算する.
     *
     * @return IDノード
     */
    @Query("""
        MERGE (i: Id{ name: "history" })
        ON CREATE SET i.value = 1
        ON MATCH SET i.value = i.value + 1
        return i
    """)
    fun calculateNextHistoryId(): IdNode

}