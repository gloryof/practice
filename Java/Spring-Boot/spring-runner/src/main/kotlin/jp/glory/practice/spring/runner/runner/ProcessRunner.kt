package jp.glory.practice.spring.runner.runner

import jp.glory.practice.spring.runner.process.BaseProcess
import jp.glory.practice.spring.runner.process.ProcessParameter
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class ProcessRunner(
    private val applicationContext: ApplicationContext
) : ApplicationRunner {
    private val logger  = LoggerFactory.getLogger(this::class.java)

    override fun run(args: ApplicationArguments) {
        val params = extractParam(args)
        loggingStart(params)
        applicationContext.getBean(params.batchName, BaseProcess::class.java)
            .let { it.execute(params) }
        loggingEnd(params)
    }

    private fun extractParam(
        args: ApplicationArguments
    ): ProcessParameter {
        val nameKey = "name"
        val values = args.optionNames
            .associateWith { args.getOptionValues(it) }

        return ProcessParameter(
            batchName = values.getValue(nameKey).first(),
            values = values.filterNot { it.key == nameKey }
        )
    }

    private fun loggingStart(param: ProcessParameter) {
        logger.info("[Runner][Start]${param.batchName}")
    }

    private fun loggingEnd(param: ProcessParameter) {
        logger.info("[Runner][End]${param.batchName}")
    }
}