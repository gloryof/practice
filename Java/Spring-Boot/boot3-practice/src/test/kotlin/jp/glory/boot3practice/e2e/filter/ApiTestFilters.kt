package jp.glory.boot3practice.e2e.filter

import io.restassured.filter.Filter

object ApiTestFilters {
    fun notAuthorizedFilter(): List<Filter> =
        listOf(
            ApiTestEnvFilter()
        )
    fun authorizedFilter(): List<Filter> =
        listOf(
            ApiTestEnvFilter(),
            ApiTestAuthFilter()
        )
}