package com.example.eventManagement.Aspect


import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component


@Aspect
@Component
class LoggingAspect {

    @Before("execution(* com.example.eventManagement.Service.*.*(..))")
    fun logBeforeMethodExecution() {
        println("Method execution logged before execution in Service Layer")
    }
}
