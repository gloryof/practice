package jp.glory.neo4jstudy.externals.neo4j.dao

import jp.glory.neo4jstudy.externals.neo4j.node.EmployeeHistoryNode
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

/**
 * 従業員イベントDao
 */
@Repository
interface EmployeeHistoryDao: Neo4jRepository<Unit, Long> {

    @Query("""
        MATCH (e:Employee {employeeId:{employeeId}})<-[r:RECORDED]-(h:EmployeeHistory)
        RETURN h
    """)
    fun findByEmployeeId(employeeId: Long): List<EmployeeHistoryNode>

    /**
     * 入社履歴ノードを登録する.
     *
     * @param historyId 履歴ID
     * @param entryAt 入社日
     * @param postId 所属部署ID
     * @param postName 所属部署名
     */
    @Query("""
        CREATE(e:EmployeeHistory:EntryHistory{
            historyId: {historyId},
            entryAt: {entryAt},
            postId: {postId},
            postName: {postName}
        })
    """)
    fun saveEntryHistory(historyId:Long, entryAt: String, postId: Long, postName:String)

    /**
     * 退職履歴ノードを登録する.
     *
     * @param historyId 履歴ID
     * @param retireAt 退職日
     */
    @Query("""
        CREATE(e:EmployeeHistory:RetireHistory{
            historyId: {historyId},
            retireAt: {retireAt}
        })
    """)
    fun saveRetireHistory(historyId:Long, retireAt: String)

    /**
     * 部署所属履歴ノードを登録する.
     *
     * @param historyId 履歴ID
     * @param joinAt 所属日
     * @param postId 部署ID
     * @param postName 部署名
     */
    @Query("""
        CREATE(e:EmployeeHistory:JoinHistory{
            historyId: {historyId},
            joinAt: {joinAt},
            postId: {postId},
            postName: {postName}
        })
    """)
    fun saveJoinHistory(historyId: Long, joinAt: String, postId: Long, postName: String)

    /**
     * 部署退任履歴ノードを登録する.
     *
     * @param historyId 履歴ID
     * @param leaveAt 退任日
     * @param postId 部署ID
     * @param postName 部署名
     */
    @Query("""
        CREATE(e:EmployeeHistory:LeaveHistory{
            historyId: {historyId},
            leaveAt: {leaveAt},
            postId: {postId},
            postName: {postName}
        })
    """)
    fun saveLeaveHistory(historyId: Long, leaveAt: String, postId: Long, postName: String)

    /**
     * 従業員ノードと従業員履歴ノードを関連づける.
     *
     * @param employeeId 従業員ID
     * @param historyId 履歴ID
     */
    @Query("""
        MATCH (e:Employee{employeeId: {employeeId}}), (h:EmployeeHistory{historyId: {historyId}})
        CREATE (e)<-[:RECORDED]-(h)
    """)
    fun relationHistoryToEmployee(employeeId: Long, historyId:Long)
}