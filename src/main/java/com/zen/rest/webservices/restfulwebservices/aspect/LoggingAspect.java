package com.zen.rest.webservices.restfulwebservices.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class LoggingAspect {

    @Before("com.zen.rest.webservices.restfulwebservices.aspect.CommonPointcutConfig.controllerPackageConfig()")
    public void logMethodCallBeforeExecution(JoinPoint joinPoint) {
        log.info("Logging method call - {}", joinPoint);
    }

    @AfterThrowing(pointcut = "com.zen.rest.webservices.restfulwebservices.aspect.CommonPointcutConfig.controllerPackageConfig()", throwing = "exception")
    public void logMethodCallAfterThrowingException(JoinPoint joinPoint, Exception exception) {
        log.error("Exception thrown by {} method - {}", joinPoint, exception.toString());
    }

    @AfterReturning(pointcut = "com.zen.rest.webservices.restfulwebservices.aspect.CommonPointcutConfig.controllerPackageConfig()", returning = "result")
    public void logMethodCallAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("{} method has returned - {}", joinPoint, result);
    }

}
