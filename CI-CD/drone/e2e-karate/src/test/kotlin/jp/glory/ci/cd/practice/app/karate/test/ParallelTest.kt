package jp.glory.ci.cd.practice.app.karate.test

import com.intuit.karate.Runner
import net.masterthought.cucumber.Configuration
import net.masterthought.cucumber.ReportBuilder
import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File


class ParallelTest {
    @Test
    fun testAll() {
        Runner.path("classpath:jp/glory/ci/cd/practice/app/karate/test")
            .outputCucumberJson(true)
            .parallel(4)
            .also {
                assertEquals(0, it.failCount, it.errorMessages)
                generateReport(it.reportDir)
            }
    }

    private fun generateReport(karateOutputPath: String) =
        FileUtils
            .listFiles(File(karateOutputPath), arrayOf("json"), true)
            .map { it.absolutePath }
            .let { ReportBuilder(it, createConfiguration()) }
            .also { it.generateReports() }

    private fun createConfiguration(): Configuration =
        Configuration(
            File("build"),
            "e2e-karate"
        )
}
