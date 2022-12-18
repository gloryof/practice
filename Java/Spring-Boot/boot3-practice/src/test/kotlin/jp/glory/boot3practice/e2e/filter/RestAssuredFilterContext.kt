package jp.glory.boot3practice.e2e.filter

import io.restassured.http.Header
import io.restassured.specification.FilterableRequestSpecification

object RestAssuredFilterContext {
    private var authTokenHeader: Header? = null

    fun isSatisfied(): Boolean = authTokenHeader != null
    fun setAuthTokenHeader(header: Header) {
        authTokenHeader = header
    }

    fun registerToRequestSpecification(
        spec: FilterableRequestSpecification
    ) {
        authTokenHeader
            ?.let { spec.header(it) }
            ?: throw IllegalStateException("authTokenHeader is null")
    }
}