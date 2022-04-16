package jp.glory.ci.cd.practice.app.it.assured

import jp.glory.ci.cd.practice.app.TestTingTags
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)

@Tags(Tag(TestTingTags.IT), Tag(TestTingTags.REST_ASSURED))
annotation class RestAssuredTest()
