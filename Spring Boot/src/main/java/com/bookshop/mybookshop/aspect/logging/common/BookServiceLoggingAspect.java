package com.bookshop.mybookshop.aspect.logging.common;

import com.bookshop.mybookshop.exception.BookstoreApiWrongParameterException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BookServiceLoggingAspect {

    @Pointcut("execution(* com.bookshop.mybookshop.services.BookService.receiveBooksByTitle(..))")
    public void receiveBooksByTitlePointcut() {
        // Pointcut.
    }

    @AfterThrowing(value = "receiveBooksByTitlePointcut()", throwing = "ex")
    public void receiveBooksByTitleAdvise(final JoinPoint joinPoint, BookstoreApiWrongParameterException ex) {
        log.warn(joinPoint.toShortString() + ". Exception message: " + ex.getLocalizedMessage());
    }
}
