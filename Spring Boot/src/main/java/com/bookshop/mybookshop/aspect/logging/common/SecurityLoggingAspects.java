package com.bookshop.mybookshop.aspect.logging.common;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class SecurityLoggingAspects {

    @AfterThrowing(value = "@annotation(com.bookshop.mybookshop.aspect.logging.annotations.AccessDeniedExceptionTraceable)", throwing = "ex")
    public void logAccessDeniedExceptionAdvise(AccessDeniedException ex) {
        log.warn("Forbidden error. Message: {}", ex.getMessage());
    }

    @AfterThrowing(value = "@annotation(com.bookshop.mybookshop.aspect.logging.annotations.AuthenticationExceptionTraceable)", throwing = "ex")
    public void logAuthenticationExceptionAdvise(AuthenticationException ex) {
        log.warn("Authentication error. Message: {}", ex.getMessage());
    }

    @AfterThrowing(value = "execution(public * com.bookshop.mybookshop.security.jwt.JWTUtil.*(..))", throwing = "ex")
    public void logExpiredTokenExceptionAdvise(ExpiredJwtException ex) {
        log.error("JWT Token is expired. Message: {}. Exception: {}", ex.getLocalizedMessage(), ex);
    }

    @AfterThrowing(value = "execution(public * com.bookshop.mybookshop.security.BookStoreUserDetailsService.loadUserByUsername*(..))", throwing = "ex")
    public void logUsernameNotFoundExceptionExceptionAdvise(JoinPoint joinPoint, UsernameNotFoundException ex) {
        String username = (String) joinPoint.getArgs()[0];
        log.error("User: {} doesn't exit. Message: {}. Exception: {}", username, ex.getLocalizedMessage(), ex);
    }

    @AfterThrowing(value = "execution(public * com.bookshop.mybookshop.security.CustomAuthenticationProvider.authenticate(..))", throwing = "ex")
    public void logBadCredentialsExceptionExceptionAdvise(BadCredentialsException ex) {
        log.error("Authentications error: {}", ex.getLocalizedMessage());
    }
}

