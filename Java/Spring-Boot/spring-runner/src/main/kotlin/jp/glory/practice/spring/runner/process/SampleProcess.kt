package jp.glory.practice.spring.runner.process

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SampleProcess : BaseProcess() {
    private val logger  = LoggerFactory.getLogger(this::class.java)
    override fun doProcess(param: ProcessParameter) {
        logger.info("test")
    }
}