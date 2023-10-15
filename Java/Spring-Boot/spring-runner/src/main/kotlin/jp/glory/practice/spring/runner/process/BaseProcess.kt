package jp.glory.practice.spring.runner.process

import jp.glory.practice.spring.runner.metrics.BatchCounterMetrics
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch

abstract class BaseProcess {
    private val logger  = LoggerFactory.getLogger(this::class.java)

    fun execute(
        metrics: BatchCounterMetrics,
        param: ProcessParameter
    ) {
        val stopWatch = StopWatch().also { it.start() }
        incrementCount(metrics, param)
        loggingStart(param)
        runCatching {
            doProcess(param)
        }
            .onSuccess { incrementSuccessCount(metrics, param) }
            .onFailure {
                incrementFailCount(metrics, param)
                throw it
            }
        loggingEnd(param)
        stopWatch.stop()
        recordDuration(
            metrics = metrics,
            param = param,
            duration = stopWatch.lastTaskTimeNanos
        )
    }


    abstract fun doProcess(param: ProcessParameter)

    private fun incrementCount(
        metrics: BatchCounterMetrics,
        param: ProcessParameter
    ) {
        metrics.incrementCount(param.batchName)
    }

    private fun incrementSuccessCount(
        metrics: BatchCounterMetrics,
        param: ProcessParameter
    ) {
        metrics.incrementSuccessCount(param.batchName)
    }


    private fun incrementFailCount(
        metrics: BatchCounterMetrics,
        param: ProcessParameter
    ) {
        metrics.incrementFailCount(param.batchName)
    }

    private fun recordDuration(
        metrics: BatchCounterMetrics,
        param: ProcessParameter,
        duration: Long
    ) {
        metrics.recordDuration(param.batchName, duration)
    }

    private fun loggingStart(param: ProcessParameter) {
        logger.info("[Process][Start]${param.batchName}")
    }

    private fun loggingEnd(param: ProcessParameter) {
        logger.info("[Process][End]${param.batchName}")
    }
}