package com.saar.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /* ================= CONTROLLER AOP ================= */

    @Before("execution(* com.saar.controller..*(..))")
    public void beforeController(JoinPoint joinPoint) {
        log.info("[AOP-BEFORE-CONTROLLER] Method: "+joinPoint);
    }

    @After("execution(* com.saar.controller..*(..))")
    public void afterController(JoinPoint joinPoint) {
        log.info("⬅️ [AOP-AFTER-CONTROLLER] Method: {}",
                joinPoint.getSignature().toShortString());
    }

    /* ================= SERVICE AOP ================= */

    @Around("execution(* com.saar.serviceImpl..*(..))")
    public Object aroundService(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        log.info("AOP-BEFORE-SERVICE Method: {} | Args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - startTime;

        log.info("AOP-AFTER-SERVICE Method: {} | Time Taken: {} ms",
                joinPoint.getSignature().toShortString(),
                timeTaken);

        return result;
    }
}
