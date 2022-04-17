package jp.glory.ci.cd.practice.app.it.rest_assured.vulunerability

import jp.glory.ci.cd.practice.app.it.rest_assured.RestAssuredTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import io.restassured.RestAssured.*
import jp.glory.ci.cd.practice.app.it.rest_assured.filter.ApiTestFilters
import org.junit.jupiter.api.assertDoesNotThrow

@RestAssuredTest
internal class RandomSeedTest {
    @Test
    @DisplayName("Success return number")
    fun returnNumber() {
        val actual = given()
            .filters(ApiTestFilters.requiredFilters())
        .`when`()
            .get("/vulnerability/random-seed")
            .asString()

        assertDoesNotThrow {
            actual.toInt()
        }
    }
}