package jp.glory.neo4jstudy.externals.neo4j.dao

import jp.glory.neo4jstudy.externals.neo4j.node.EmployeeNode
import jp.glory.neo4jstudy.externals.neo4j.result.EmployeeResult
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

/**
 * 従業員のDao.
 */
@Repository
interface EmployeeDao : Neo4jRepository<EmployeeNode, Long> {

    /**
     * 従業員ノードをマージする.
     *
     * @param employeeId 従業員ID
     * @param lastName 姓
     * @param firstName 名
     * @param age 年齢
     */
    @Query(
        """
           MERGE (e: Employee{employeeId: {employeeId}})
           SET
              e.lastName = {lastName},
              e.firstName = {firstName},
              e.age = {age}

        """
    )
    fun merge(employeeId:Long, lastName: String, firstName: String, age: Int)

    /**
     * 従業員IDをキーに削除する.
     *
     * @param employeeId 従業員ID
     */
    @Query("MATCH (e: Employee{employeeId: {employeeId}}) DELETE e")
    fun deleteByEmployeeId(employeeId: Long)

    @Query("""
        MATCH (p:Post)<-[:JOIN]-(e:Employee)
        RETURN e AS employee, p.postId AS joinPostId
    """)
    fun findAllEmployeesWithPost(): List<EmployeeResult>
}