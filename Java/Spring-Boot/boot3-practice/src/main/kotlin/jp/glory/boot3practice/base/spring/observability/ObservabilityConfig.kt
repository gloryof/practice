package jp.glory.boot3practice.base.spring.observability

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationRegistry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration

@Configuration
class ObservabilityConfig (
    private val observationRegistry: ObservationRegistry
){
    var logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun configure() {
        observationRegistry.observationConfig()
            .apply {
                observationHandler(CustomObservationHandler())
            }
        Observation.createNotStarted("CustomObservation", observationRegistry)
            .observe {
                logger.info("Observe is started.")
            }
    }
}