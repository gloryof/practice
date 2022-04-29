package jp.glory.ci.cd.practice.app.suite

import io.restassured.RestAssured
import io.restassured.parsing.Parser
import jp.glory.ci.cd.practice.app.TestTingTags
import jp.glory.ci.cd.practice.app.it.util.EnvironmentExtractor
import org.junit.platform.suite.api.IncludeTags
import org.junit.platform.suite.api.Suite
import org.junit.platform.suite.api.SuiteDisplayName

@Suite
@SuiteDisplayName("IT(REST assured)")
@IncludeTags(TestTingTags.IT, TestTingTags.REST_ASSURED)
@TestBasePackage
class RestAssuredTestSuite {
    init {
        RestAssured.baseURI = EnvironmentExtractor.getTargetUrl()
        RestAssured.port = EnvironmentExtractor.getTargetPort()
        RestAssured.defaultParser = Parser.JSON
    }
}