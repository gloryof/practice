package jp.glory.ci.cd.practice.app.it.rest_assured.vulunerability

import jp.glory.ci.cd.practice.app.it.rest_assured.RestAssuredTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import jp.glory.ci.cd.practice.app.it.rest_assured.filter.ApiTestFilters
import org.junit.jupiter.api.assertDoesNotThrow

@RestAssuredTest
internal class RandomSeedTest {
    @Test
    @DisplayName("Success return number")
    fun returnNumber() {
        val actual = Given {
            filters(ApiTestFilters.requiredFilters())
        } When  {
            get("/vulnerability/random-seed")
        } Extract {
            asString()
        }

        assertDoesNotThrow {
            actual.toInt()
        }
    }
}