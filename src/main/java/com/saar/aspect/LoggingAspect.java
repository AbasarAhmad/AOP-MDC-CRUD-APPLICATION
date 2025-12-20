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

        String firstName = "-";
        String lastName  = "-";
        String email     = "-";
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

            /* ================= HTTP Info ================= */
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                httpMethod = request.getMethod();
            }

            /* ================= MDC ================= */
            MDC.put("correlationId", UUID.randomUUID().toString());
            MDC.put("httpMethod", httpMethod);
            MDC.put("firstName", firstName);
            MDC.put("lastName", lastName);
            MDC.put("email", email);

            // 🔥 DEFAULT layer
            MDC.put("layerType", "Application");

            log.info("➡️ AOP BEFORE CONTROLLER");

            Object result = joinPoint.proceed();

            log.info("⬅️ AOP AFTER CONTROLLER | Time Taken: {} ms",
                    System.currentTimeMillis() - startTime);

            return result;

        } finally {
            MDC.clear();
        }
    }
}
