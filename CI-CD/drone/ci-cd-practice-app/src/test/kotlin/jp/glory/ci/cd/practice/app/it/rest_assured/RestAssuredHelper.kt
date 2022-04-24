package jp.glory.ci.cd.practice.app.it.rest_assured

import io.restassured.http.Header
import io.restassured.http.Headers

object RestAssuredHelper {
    /**
     * For RUD headers in CRUD.
     */
    fun createRudHeaders(
        appendHeaders: List<Header> = emptyList()
    ): Headers {
        val headers = appendHeaders + listOf(
            Header("Content-Type", "application/json")
        )
        return Headers(headers)
    }

}