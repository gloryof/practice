package jp.glory.boot3practice.base.spring.observability

import io.micrometer.common.KeyValue
import io.micrometer.common.KeyValues
import org.springframework.http.server.reactive.observation.DefaultServerRequestObservationConvention
import org.springframework.http.server.reactive.observation.ServerRequestObservationContext

class CustomServerRequestObservationConvention : DefaultServerRequestObservationConvention() {
    override fun getLowCardinalityKeyValues(
        context: ServerRequestObservationContext
    ): KeyValues {
        return super.getLowCardinalityKeyValues(context)
            .and(testValue())
    }

    override fun getHighCardinalityKeyValues(
        context: ServerRequestObservationContext
    ): KeyValues =
        super.getHighCardinalityKeyValues(context)
            .and(getHighCardinalityAttributes(context))

    private fun testValue(): KeyValue =
        KeyValue.of("test-key", "test-value")

    private fun getHighCardinalityAttributes(
        context: ServerRequestObservationContext
    ): List<KeyValue> {
        val values = mutableListOf<KeyValue>()

        userId(context)?.let { values.add(it) }

        return values.toList()
    }

    private fun userId(
        context: ServerRequestObservationContext
    ): KeyValue? {
        val keyValue = context.attributes["user-id"]
            ?.let {
                KeyValue.of("user-id", it as String)
            }

        return keyValue
    }

}