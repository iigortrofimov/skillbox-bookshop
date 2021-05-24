package com.bookshop.mybookshop.aspect.logging.common;

import com.bookshop.mybookshop.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class RepositoryLoggingAspect {

    private static final String TARGET_TYPE = "Repository";


    @Pointcut("execution(* save*(..))")
    public void save() {
        // Pointcut.
    }

    @Pointcut("execution(* delete*(..))")
    public void delete() {
        // Pointcut.
    }

    @Before(value = "within(org.springframework.data.repository.*) && (save() || delete())")
    public void beforeRepositoryCall(final JoinPoint joinPoint) {
        log.debug(LoggingUtils.createCallExecutionMessage(TARGET_TYPE, joinPoint));
        log.info(LoggingUtils.createShortCallExecutionMessage(TARGET_TYPE, joinPoint));
    }
}
