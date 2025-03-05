package com.java.app.enterprise_application.utils.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@within(Log)") // Перехватываем все методы классов с аннотацией @Log
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        // Логируем вход в метод
        logger.info("Entering method: {} with arguments: {}", methodName, args);

        try {
            // Выполняем метод
            Object result = joinPoint.proceed();

            // Логируем выход из метода
            logger.info("Exiting method: {} with result: {}", methodName, result);
            return result;
        } catch (Exception e) {
            // Логируем исключение, если оно возникло
            logger.error("Exception in method: {}", methodName, e);
            throw e;
        }
    }
}
