package jp.glory.practicegraphql.app.product.adaptor.web.controller

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import jp.glory.practicegraphql.app.base.adaptor.web.error.WebError
import jp.glory.practicegraphql.app.base.adaptor.web.error.toWebError
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Member
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Product
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Products
import jp.glory.practicegraphql.app.product.adaptor.web.graphql.schema.Service
import jp.glory.practicegraphql.app.product.usecase.*
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.cloud.sleuth.annotation.SpanTag
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class FindProductController(
    private val findProduct: FindProductUseCase,
    private val findMember: FindMemberUseCase,
    private val findService: FindServiceUseCase
) {

    @QueryMapping
    @NewSpan
    fun findAllProducts(): Products =
        findAll()
            .map { it.products.map { prod -> Product(prod) } }
            .mapBoth(
                success = { Products(it) },
                failure = { throw it.createException() }
            )

    @QueryMapping
    @NewSpan
    fun findProduct(
        @SpanTag("param.id") @Argument id: String,
    ): Product =
        findById(id)
            .mapBoth(
                success = { Product(it) },
                failure = { throw it.createException() }
            )

    @BatchMapping
    @NewSpan
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
    @NewSpan
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
