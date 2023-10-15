package jp.glory.practice.spring.runner.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Timer
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class BatchCounterMetrics(
    private val registry: MeterRegistry
) {
    private val allCounters = MetricsCounter(registry, CounterType.All)
    private val successCounters = MetricsCounter(registry, CounterType.Success)
    private val failCounters = MetricsCounter(registry, CounterType.Fail)
    private val timers = mutableMapOf<String, Timer>()

    fun incrementCount(batchName: String) {
        allCounters.getCounter(batchName).increment()
    }

    fun incrementSuccessCount(batchName: String) {
        successCounters.getCounter(batchName).increment()
    }

    fun incrementFailCount(batchName: String) {
        failCounters.getCounter(batchName).increment()
    }

    fun recordDuration(
        batchName: String,
        nanos: Long
    ) {
        getTimer(batchName).record(Duration.ofNanos(nanos))
    }

    private fun getTimer(batchName: String): Timer {
        val timer = timers[batchName]
        if (timer != null) {
            return timer
        }

        val tags = listOf(Tag.of("type", "timer"))
        val newTimer = registry.timer("app.practice.batch.duration.nano", tags)
        timers[batchName] = newTimer

        return newTimer
    }

    private class MetricsCounter(
        private val registry: MeterRegistry,
        private val type: CounterType
    ) {
        private val counters = mutableMapOf<String, Counter>()

        fun getCounter(
            batchName: String,
        ): Counter {
            val counter = counters[batchName]
            if (counter != null) {
                return counter
            }

            val tags = listOf(Tag.of("type", type.name.lowercase()))
            val newCounter = registry.counter("app.practice.batch.count", tags)
            counters[batchName] = newCounter

            return newCounter
        }
    }

    private enum class CounterType {
        All,
        Success,
        Fail
    }
}
