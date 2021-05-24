package com.bookshop.mybookshop.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtils {

    public void fullyAuthenticated(HttpServletRequest request, HttpServletResponse response,
                                   UserDetails userDetails, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }
}
