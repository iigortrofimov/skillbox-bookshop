package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.aspect.logging.annotations.AccessDeniedExceptionTraceable;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    @AccessDeniedExceptionTraceable
    public void handle
            (HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.sendRedirect("/403");
    }
}
