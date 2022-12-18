package jp.glory.boot3practice.e2e.filter


import io.restassured.RestAssured
import io.restassured.filter.Filter
import io.restassured.filter.FilterContext
import io.restassured.filter.OrderedFilter
import io.restassured.parsing.Parser
import io.restassured.response.Response
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.FilterableResponseSpecification

class ApiTestEnvFilter : Filter, OrderedFilter {
    override fun filter(
        requestSpec: FilterableRequestSpecification?,
        responseSpec: FilterableResponseSpecification?,
        ctx: FilterContext?
    ): Response {
        if (requestSpec == null || ctx == null) {
            throw IllegalStateException("Required parameter is null")
        }

        requestSpec.baseUri(EnvironmentExtractor.getTargetUrl())
        RestAssured.baseURI = EnvironmentExtractor.getTargetUrl()

        RestAssured.port = EnvironmentExtractor.getTargetPort()
        requestSpec.port(EnvironmentExtractor.getTargetPort())

        RestAssured.defaultParser = Parser.JSON

        return ctx.next(requestSpec, responseSpec)
    }

    override fun getOrder(): Int = 1
}
