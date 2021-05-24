package com.bookshop.mybookshop.aspect.logging.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ExceptionHandlerLoggingAspect {

    @Pointcut("execution(public * com.bookshop.mybookshop.exception.GlobalExceptionHandlerController.*(..))")
    public void allGlobalExceptionHandlerControllerMethodsPointcut() {
        // Pointcut.
    }

    @Before("allGlobalExceptionHandlerControllerMethodsPointcut()")
    public void beforeCatchExceptionAdvise(final JoinPoint joinPoint) {
        final Exception exc = (Exception) joinPoint.getArgs()[0];
        log.debug(exc.getLocalizedMessage(), exc);
    }
}
