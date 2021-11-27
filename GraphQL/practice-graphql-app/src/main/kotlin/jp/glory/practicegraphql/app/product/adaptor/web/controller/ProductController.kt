package jp.glory.practicegraphql.app.product.adaptor.web.controller

import com.github.michaelbull.result.*
import jp.glory.practicegraphql.app.base.adaptor.web.error.NotFoundError
import jp.glory.practicegraphql.app.base.adaptor.web.error.WebError
import jp.glory.practicegraphql.app.base.adaptor.web.error.toWebError
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Member
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Product
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Products
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Service
import jp.glory.practicegraphql.app.product.usecase.*
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class ProductController(
    private val findProduct: FindProductUseCase,
    private val findMember: FindMemberUseCase,
    private val findService: FindServiceUseCase
) {

    @QueryMapping
    fun findAllProducts(): Products =
        findAll()
            .map { it.products.map { prod -> Product(prod) } }
            .mapBoth(
                success = { Products(it) },
                failure = { throw it.createException() }
            )

    @QueryMapping
    fun findProduct(
        @Argument id: String,
    ): Product =
        findById(id)
            .mapBoth(
                success = { Product(it) },
                failure = { throw it.createException() }
            )

    @BatchMapping
    fun members(
        products: List<Product>
    ): Map<Product, List<Member>> =
        products
            .flatMap { it.memberIds }
            .let { findMembers(it) }
            .map { mapToMembers(products, it) }
            .mapBoth(
                success = { it },
                failure = { throw it.createException() }
            )

    @BatchMapping
    fun services(
        products: List<Product>
    ): Map<Product, List<Service>> =
        products
            .flatMap { it.serviceIds }
            .let { findServices(it) }
            .map { mapToServices(products, it) }
            .mapBoth(
                success = { it },
                failure = { throw it.createException() }
            )

    private fun findAll(): Result<ProductsSearchResult, WebError> =
        findProduct.findAll()
            .mapError { toWebError(it) }

    private fun findById(id: String): Result<ProductSearchResult, WebError> =
        findProduct.findById(id)
            .mapError { toWebError(it) }
            .flatMap {
                if (it == null) {
                    Err(NotFoundError("Can not found product"))
                } else {
                    Ok(it)
                }
            }

    private fun findMembers(ids: List<String>): Result<List<Member>, WebError> =
        findMember.findByIds(ids)
            .map { it.results.map { result -> Member(result) } }
            .mapError { toWebError(it) }

    private fun findServices(ids: List<String>): Result<List<Service>, WebError> =
        findService.findByIds(ids)
            .map { it.results.map { result -> Service(result) } }
            .mapError { toWebError(it) }

    private fun mapToMembers(
        products: List<Product>,
        members: List<Member>
    ): Map<Product, List<Member>> {
        val memberMap = members.associateBy { it.id }
        return products
            .associate { product ->
                product.memberIds
                    .mapNotNull { memberMap[it] }
                    .let { Pair(product, it) }
            }
    }

    private fun mapToServices(
        products: List<Product>,
        services: List<Service>
    ): Map<Product, List<Service>> {
        val serviceMap = services.associateBy { it.id }
        return products
            .associate { product ->
                product.serviceIds
                    .mapNotNull { serviceMap[it] }
                    .let { Pair(product, it) }
            }
    }
}