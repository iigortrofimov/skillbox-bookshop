package com.bookshop.mybookshop.security.jwt;

import com.bookshop.mybookshop.security.BookStoreUserDetails;
import com.bookshop.mybookshop.security.BookStoreUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTRequestFilter extends OncePerRequestFilter {

    private final BookStoreUserDetailsService bookStoreUserDetailsService;
    private final JWTUtil jwtUtil;
    private final JWTTokenBlackList jwtTokenBlackList;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        BookStoreUserDetails userDetails;
        String token = null;
        String username = null;
        Cookie[] cookies = request.getCookies();

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
                try {
                    username = jwtUtil.extractUsername(token);
                } catch (ExpiredJwtException exception) {
                    log.error("JWT Token is expired. Message: {}", exception.getLocalizedMessage());
                    token = null;
                }
            }
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Login attempt: Detected JWT Token. Token: {}", token);

        if (jwtTokenBlackList.findByToken(token).isPresent()) {
            log.info("Token was found in BlackList");
            filterChain.doFilter(request, response);
            return;
        }

        if (username == null) {
            log.info("Token doesn't contain user data");
            filterChain.doFilter(request, response);
            return;
        }

        userDetails = (BookStoreUserDetails) bookStoreUserDetailsService.loadUserByUsername(username);
        log.info("User from JWT Token: {} is found", userDetails.getUsername());

        if (!jwtUtil.validateToken(token, userDetails)) {
            log.info("Invalid token");
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        log.info("Authentication's completed with next credentials: Login : {}, Authorities: {}, Ip-address: {}",
                userDetails.getUsername(), userDetails.getAuthorities(), request.getRemoteAddr());

        filterChain.doFilter(request, response);
    }
}
