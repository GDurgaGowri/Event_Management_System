package com.example.eventManagement.Aspect

import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class GlobalExceptionHandlingAspect {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @AfterThrowing(pointcut = "execution(* com.example.eventManagement.*.*(..))", throwing = "ex")
    fun handleServiceExceptions(ex: Exception) {
        logger.error("Exception in Service layer: ${ex.message}", ex)
    }
}
