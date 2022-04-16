package jp.glory.ci.cd.practice.app.suite

import jp.glory.ci.cd.practice.app.TestTingTags
import org.junit.platform.suite.api.ExcludeTags
import org.junit.platform.suite.api.Suite
import org.junit.platform.suite.api.SuiteDisplayName

@Suite
@SuiteDisplayName("UT")
@ExcludeTags(TestTingTags.IT)
@TestBasePackage
class UtTestSuite