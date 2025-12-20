package com.saar.aspect;

import com.saar.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.saar.controller..*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String firstName = null;
        String lastName  = null;
        String email     = null;
        String httpMethod = null;

        try {
            /* ================= Extract Request Body ================= */
            for (Object arg : joinPoint.getArgs()) {
                if (arg instanceof Student student) {
                    firstName = student.getFirstName();
                    lastName  = student.getLastName();
                    email     = student.getEmail();
                }
            }

            /* ================= Extract HTTP Method ================= */
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                httpMethod = request.getMethod();
            }
         // Generate correlation id 
            String correlationId = UUID.randomUUID().toString(); 
            MDC.put("correlationId", correlationId);

            /* ================= SET MDC ================= */
            if (httpMethod != null) { // ensures only HTTP/Postman calls
                MDC.put("httpMethod", httpMethod);
                MDC.put("firstName", firstName != null ? firstName : "-");
                MDC.put("lastName",  lastName  != null ? lastName  : "-");
                MDC.put("email",     email     != null ? email     : "-");
            }

            log.info("➡️ AOP BEFORE CONTROLLER");

            Object result = joinPoint.proceed();

            long timeTaken = System.currentTimeMillis() - startTime;

            log.info("⬅️ AOP AFTER CONTROLLER | Time Taken: {} ms", timeTaken);

            return result;

        } finally {
            /* ================= VERY IMPORTANT ================= */
            MDC.clear(); // prevents memory/thread leak
        }
    }
}
