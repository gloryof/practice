package jp.glory.practice.spring.runner.process

import org.slf4j.LoggerFactory

abstract class BaseProcess {
    private val logger  = LoggerFactory.getLogger(this::class.java)

    fun execute(param: ProcessParameter) {
        loggingStart(param)
        doProcess(param)
        loggingEnd(param)
    }

    abstract fun doProcess(param: ProcessParameter)

    private fun loggingStart(param: ProcessParameter) {
        logger.info("[Process][Start]${param.batchName}")
    }

    private fun loggingEnd(param: ProcessParameter) {
        logger.info("[Process][End]${param.batchName}")
    }
}