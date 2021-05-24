package com.bookshop.mybookshop.aspect.logging.access;

import com.bookshop.mybookshop.security.jwt.JWTToken;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AccessLoginAspects {

    @Pointcut("execution(* com.bookshop.mybookshop.security.jwt.JWTTokenBlackList.findByToken(..))")
    public void findByTokenInTokenBlackListPointcut() {
        // Pointcut.
    }

    @Pointcut("execution(* com.bookshop.mybookshop.security.SecurityUtils.fullyAuthenticated(..))")
    public void fullyAuthenticatedPointcut() {
        // Pointcut.
    }

    @AfterReturning(pointcut = "findByTokenInTokenBlackListPointcut()", returning = "result")
    public void resultFromJWTBlackListAdvise(Optional<JWTToken> result) {
        result.ifPresent(res -> log.info("Token was found in BlackList"));
    }

    @AfterReturning(value = "@annotation(com.bookshop.mybookshop.aspect.logging.annotations.JWTUserDetailsTraceable)", returning = "userDetails")
    public void trackUserDetailsAdvise(UserDetails userDetails) {
        log.info("User from JWT Token: {} is found.", userDetails.getUsername());
    }

    @AfterReturning(value = "@annotation(com.bookshop.mybookshop.aspect.logging.annotations.JWTTokenTraceable)", returning = "isValid")
    public void checkJWTTokenAdvise(Boolean isValid) {
        if (!isValid) {
            log.info("Invalid token");
        }
    }

    @Before("fullyAuthenticatedPointcut()")
    public void fullyAuthenticatedAdvise(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        UserDetails userDetails = (UserDetails) args[2];
        log.info("Authentication's completed with next credentials: Login : {}, Authorities: {}, Ip-address: {}",
                userDetails.getUsername(), userDetails.getAuthorities(), request.getRemoteAddr());
    }
}
