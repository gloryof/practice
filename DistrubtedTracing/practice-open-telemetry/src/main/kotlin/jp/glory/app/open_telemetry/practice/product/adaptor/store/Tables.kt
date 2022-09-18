package jp.glory.app.open_telemetry.practice.product.adaptor.store

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object MemberTable : Table("members") {
    val id = varchar("id", 50)
    val givenName = varchar("given_name", length = 50)
    val familyName = varchar("family_name", length = 50)
    val birthDay = date("birth_day")

    override val primaryKey = PrimaryKey(id, name = "pk_member_id")
}

object ProductTable : Table("products") {
    val id = varchar("id", 50)
    val code = varchar("code", length = 50)
    val name = varchar("name", length = 50)

    override val primaryKey = PrimaryKey(id, name = "pk_product_id")
}

object ServiceTable : Table("services") {
    val id = varchar("id", 50)
    val name = varchar("name", length = 50)
    val kind = integer("kind")

    override val primaryKey = PrimaryKey(id, name = "pk_service_id")
}

object ProductMemberTable : Table("product_members") {
    val productId = varchar("product_id", 50)
    val memberId = varchar("member_id", 50)
}

object ProductServiceTable : Table("product_services") {
    val productId = varchar("product_id", 50)
    val serviceId = varchar("service_id", 50)
}