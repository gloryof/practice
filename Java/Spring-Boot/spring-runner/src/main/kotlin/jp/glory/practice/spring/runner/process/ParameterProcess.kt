package jp.glory.practice.spring.runner.process

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ParameterProcess : BaseProcess() {
    private val logger  = LoggerFactory.getLogger(this::class.java)
    override fun doProcess(param: ProcessParameter) {
        listOf("value1", "value2")
            .map { logger.info("[$it : ${param.getParamValues(it)}]") }
    }
}