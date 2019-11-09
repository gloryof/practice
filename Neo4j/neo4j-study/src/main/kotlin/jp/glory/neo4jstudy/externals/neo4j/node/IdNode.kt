package jp.glory.neo4jstudy.externals.neo4j.node

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property
import org.springframework.data.neo4j.annotation.QueryResult

/**
 * IDを示すノード.
 *
 * @param name 名前
 * @param value 値
 */
@NodeEntity(label = "Id")
data class IdNode(
    @Id @GeneratedValue var id: Long = 0,
    @Property("name") var name: String = "",
    @Property("value") var value: Long = 0)