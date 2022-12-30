package jp.glory.boot3practice.base.spring.observability

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CustomObservationHandler : ObservationHandler<Observation.Context> {
    var logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun onStart(context: Observation.Context) {
        logging(
            methodName = "onStart",
            context = context
        )
    }

    override fun onEvent(event: Observation.Event, context: Observation.Context) {

        logging(
            methodName = "onError",
            context = context,
            keyValue = mapOf(
                "name" to event.name,
                "contextualName" to event.contextualName
            )
        )
    }

    override fun onError(context: Observation.Context) {
        logging(
            methodName = "onError",
            context = context
        )
    }

    override fun onStop(context: Observation.Context) {
        logging(
            methodName = "onStop",
            context = context
        )
    }

    override fun onScopeOpened(context: Observation.Context) {
        logging(
            methodName = "onScopeOpened",
            context = context
        )
    }

    override fun onScopeClosed(context: Observation.Context) {
        logging(
            methodName = "onScopeClosed",
            context = context
        )
    }

    override fun supportsContext(context: Observation.Context): Boolean = true

    private fun logging(
        methodName: String,
        context: Observation.Context,
        keyValue: Map<String, String> = emptyMap()
    ) {
        if (!logger.isDebugEnabled) {
            return
        }

        val extraValue = keyValue.toString()
        logger.info("Do $methodName context ${context.name} observation($extraValue)")
    }
}