package jp.glory.ci.cd.practice.app.suite

import jp.glory.ci.cd.practice.app.TestTingTags
import org.junit.platform.suite.api.IncludeTags
import org.junit.platform.suite.api.Suite
import org.junit.platform.suite.api.SuiteDisplayName

@Suite
@SuiteDisplayName("IT(REST assured)")
@IncludeTags(TestTingTags.IT, TestTingTags.REST_ASSURED)
@TestBasePackage
class RestAssuredTestSuite {
}