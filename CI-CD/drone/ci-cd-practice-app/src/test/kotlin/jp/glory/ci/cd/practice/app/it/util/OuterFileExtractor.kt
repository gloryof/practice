package jp.glory.ci.cd.practice.app.it.util

import java.nio.file.Files
import java.nio.file.Paths

object OuterFileExtractor {
    fun readString(path: String): String =
        Paths.get(EnvironmentExtractor.getTestFileLocation(), path)
            .let { Files.readString(it) }
}