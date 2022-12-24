package jp.glory.boot3practice.base.spring.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration

@Aspect
@Configuration
class UseCaseAspect {
    var logger: Logger = LoggerFactory.getLogger("UseCaseLog")

    @Around("@within(jp.glory.boot3practice.base.use_case.UseCase)")
    fun weaving(joinPoint: ProceedingJoinPoint): Any {
        val useCaseName = joinPoint.target::class.simpleName
        logger.info("Start $useCaseName")
        try {
            return joinPoint.proceed()
                .also {
                    logger.info("Success $useCaseName")
                }
        } catch (e: Exception) {
            logger.info("Failed $useCaseName")
            throw e
        }
    }
}