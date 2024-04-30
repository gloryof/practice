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
	runApplication<PracticePyroscopeApplication>(*args)

	PyroscopeAgent.start(
		Config.Builder()
			.apply {
				applicationName = "practice-pyroscope"
				profilingEvent = EventType.ALLOC
				format = Format.JFR
				serverAddress = "http://localhost:30080"
			}
			.build()
	)
}
