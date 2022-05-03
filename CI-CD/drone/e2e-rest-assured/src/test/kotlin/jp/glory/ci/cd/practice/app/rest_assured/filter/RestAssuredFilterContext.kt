package jp.glory.ci.cd.practice.app.rest_assured.filter

import io.restassured.http.Header
import io.restassured.specification.FilterableRequestSpecification

object RestAssuredFilterContext {
    private var csrfTokenHeader: Header? = null
    private var authTokenHeader: Header? = null

    fun isSatisfied(): Boolean =
        csrfTokenHeader != null && authTokenHeader != null

    fun setCsrfTokenHeader(header: Header) {
        csrfTokenHeader = header
    }

    fun setAuthTokenHeader(header: Header) {
        authTokenHeader = header
    }

    fun registerToRequestSpecification(
        spec: FilterableRequestSpecification
    ) {
        csrfTokenHeader
            ?.let { spec.header(it) }
            ?: throw IllegalStateException("csrfTokenHeader is null")

        authTokenHeader
            ?.let { spec.header(it) }
            ?: throw IllegalStateException("authTokenHeader is null")
    }
}