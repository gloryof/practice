package jp.glory.app.coverage.practice.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.app.coverage.practice.base.domain.DomainUnknownError
import jp.glory.app.coverage.practice.product.domain.model.*
import jp.glory.app.coverage.practice.product.domain.repository.ProductRepository
import org.jetbrains.exposed.sql.*

class ProductRepositoryImpl : ProductRepository {
    override fun findAll(): Result<List<Product>, DomainUnknownError> {
        val rows = ProductTable.selectAll()
            .toList()

        if (rows.isEmpty()) {
            return Ok(emptyList())
        }
        val keys = rows.map { it[ProductTable.id] }

        val serviceIdMap = findServiceIdMap(keys)
        val memberIdMap = findMemberIdMap(keys)

        return rows
            .map {
                toDomainModel(
                    row = it,
                    serviceIdMap = serviceIdMap,
                    memberIdMap = memberIdMap
                )
            }
            .let { Ok(it) }
    }

    override fun findById(
        id: ProductID
    ): Result<Product?, DomainUnknownError> {
        val row = ProductTable
            .select { ProductTable.id eq id.value }
            .firstOrNull()
            ?: return Ok(null)

        val key = listOf(id.value)

        val serviceIdMap = findServiceIdMap(key)
        val memberIdMap = findMemberIdMap(key)

        return toDomainModel(
            row = row,
            serviceIdMap = serviceIdMap,
            memberIdMap = memberIdMap
        )
            .let { Ok(it) }
    }

    override fun findByProductCode(code: ProductCode): Result<List<Product>, DomainUnknownError> {
        val rows = ProductTable.select { ProductTable.code eq code.value }
            .toList()

        if (rows.isEmpty()) {
            return Ok(emptyList())
        }
        val keys = rows.map { it[ProductTable.id] }

        val serviceIdMap = findServiceIdMap(keys)
        val memberIdMap = findMemberIdMap(keys)

        return rows
            .map {
                toDomainModel(
                    row = it,
                    serviceIdMap = serviceIdMap,
                    memberIdMap = memberIdMap
                )
            }
            .let { Ok(it) }
    }

    override fun register(id: ProductID, event: RegisterProductEvent): Result<ProductID, DomainUnknownError> {
        ProductTable.insert {
            it[ProductTable.id] = id.value
            it[code] = event.code.value
            it[name] = event.name.value
        }

        event.memberIDs.value.forEach {
            ProductMemberTable.insert { state ->
                state[productId] = id.value
                state[memberId] = it.value
            }
        }

        event.serviceIDs.value.forEach {
            ProductServiceTable.insert { state ->
                state[productId] = id.value
                state[serviceId] = it.value
            }
        }

        return Ok(id)
    }

    override fun save(event: UpdateProductEvent): Result<ProductID, DomainUnknownError> {
        val idValue = event.id.value
        ProductTable.update({ ProductTable.id eq idValue }) {
            it[code] = event.code.value
            it[name] = event.name.value
        }

        ProductMemberTable.deleteWhere { ProductMemberTable.productId eq idValue }
        event.memberIDs.value.forEach {
            ProductMemberTable.insert { state ->
                state[productId] =idValue
                state[memberId] = it.value
            }
        }

        ProductServiceTable.deleteWhere { ProductServiceTable.productId eq idValue }
        event.serviceIDs.value.forEach {
            ProductServiceTable.insert { state ->
                state[productId] = idValue
                state[serviceId] = it.value
            }
        }

        return Ok(event.id)
    }

    private fun toDomainModel(
        row: ResultRow,
        serviceIdMap: Map<String, List<String>>,
        memberIdMap: Map<String, List<String>>,
    ): Product {
        val idValue = row[ProductTable.id]
        return Product(
            id = ProductID(idValue),
            code = ProductCode(row[ProductTable.code]),
            name = ProductName(row[ProductTable.name]),
            serviceIDs = toServiceIds(serviceIdMap[idValue]),
            memberIDs = toMemberIds(memberIdMap[idValue])
        )
    }

    private fun toServiceIds(ids: List<String>?): ServiceIds =
        ids
            ?.let {
                it.map { value -> ServiceID(value) }
            }
            ?.let { ServiceIds(it) }
            ?: ServiceIds(emptyList())
    private fun toMemberIds(ids: List<String>?): MemberIds =
        ids
            ?.let {
                it.map { value -> MemberID(value) }
            }
            ?.let { MemberIds(it) }
            ?: MemberIds(emptyList())

    private fun findServiceIdMap(ids: List<String>): Map<String, List<String>> {
        val rows = ProductServiceTable
            .select { ProductServiceTable.productId inList ids }
            .toList()

        val results = mutableMapOf<String, MutableList<String>>()

        rows
            .forEach {
                results
                    .computeIfAbsent(it[ProductServiceTable.productId]) { mutableListOf() }
                    .add(it[ProductServiceTable.serviceId])
            }

        return results
            .mapValues { it.value.toList() }
            .toMap()
    }
    private fun findMemberIdMap(ids: List<String>): Map<String, List<String>> {
        val rows = ProductMemberTable
            .select { ProductMemberTable.productId inList ids }
            .toList()

        val results = mutableMapOf<String, MutableList<String>>()

        rows
            .forEach {
                results
                    .computeIfAbsent(it[ProductMemberTable.productId]) { mutableListOf() }
                    .add(it[ProductMemberTable.memberId])
            }

        return results
            .mapValues { it.value.toList() }
            .toMap()
    }
}
