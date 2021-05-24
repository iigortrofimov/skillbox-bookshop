package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.aspect.logging.annotations.AuthenticationExceptionTraceable;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @AuthenticationExceptionTraceable
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response,
                         AuthenticationException e) throws AuthenticationCredentialsNotFoundException, IOException {
        response.sendRedirect("/401");
    }
}
