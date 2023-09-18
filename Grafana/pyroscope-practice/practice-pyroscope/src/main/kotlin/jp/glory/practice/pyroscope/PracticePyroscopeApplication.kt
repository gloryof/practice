package jp.glory.practice.pyroscope

import io.pyroscope.http.Format
import io.pyroscope.javaagent.EventType
import io.pyroscope.javaagent.PyroscopeAgent
import io.pyroscope.javaagent.config.Config
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PracticePyroscopeApplication

fun main(args: Array<String>) {
	PyroscopeAgent.start(
		Config.Builder()
			.setApplicationName("practice-app")
			.setProfilingEvent(EventType.ITIMER)
			.setFormat(Format.JFR)
			.setServerAddress("http://localhost:30040")
			.build()
	)
	runApplication<PracticePyroscopeApplication>(*args)
}
