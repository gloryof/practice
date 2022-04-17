package jp.glory.ci.cd.practice.app.it.rest_assured.filter

import io.restassured.filter.Filter

object ApiTestFilters {
    fun requiredFilters(): List<Filter> =
        listOf(
            ApiTestEnvFilter(),
            ApiTestAuthFilter()
        )
}