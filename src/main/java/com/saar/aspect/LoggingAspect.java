package com.saar.aspect;

import com.saar.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /* ================= CONTROLLER AOP (TIME + DATA + METHOD) ================= */

    @Around("execution(* com.saar.controller..*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	log.info("AOP Method called Before Controller !!!");
        long startTime = System.currentTimeMillis();

        String firstName = null;
        String lastName  = null;
        String email     = null;
        String httpMethod = null;

        // 1️ Extract request body values
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Student student) {
                firstName = student.getFirstName();
                lastName  = student.getLastName();
                email     = student.getEmail();
            }
        }

        // 2 Extract HTTP method (GET, POST, PUT, DELETE)
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            httpMethod = request.getMethod();
        }

        log.info("[AOP-BEFORE-CONTROLLER]");
        log.info("HTTP Method : {}", httpMethod);
        log.info("Request Data → firstName={}, lastName={}, email={}",firstName, lastName, email);

        // 3️ Actual controller execution
        Object result = joinPoint.proceed();
        
        log.info("AOP Method called After Controller !!!");

        long timeTaken = System.currentTimeMillis() - startTime;

        log.info("[AOP-AFTER-CONTROLLER] Method: {} | Time Taken: {} ms",
                joinPoint.getSignature().toShortString(),
                timeTaken);

        return result;
    }
}
