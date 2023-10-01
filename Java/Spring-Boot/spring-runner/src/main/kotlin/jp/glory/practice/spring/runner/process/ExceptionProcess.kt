package jp.glory.practice.spring.runner.process

import jp.glory.practice.spring.runner.exception.SystemException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ExceptionProcess : BaseProcess() {
    private val logger  = LoggerFactory.getLogger(this::class.java)
    override fun doProcess(param: ProcessParameter) {
        param.getParamValue("exception")
            .let { it.toBoolean() }
            .apply { if (this) { throw SystemException() } }
    }
}