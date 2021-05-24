package com.bookshop.mybookshop.security.jwt;

import com.bookshop.mybookshop.security.BookStoreUserDetails;
import com.bookshop.mybookshop.security.BookStoreUserDetailsService;
import com.bookshop.mybookshop.security.SecurityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

    private final BookStoreUserDetailsService bookStoreUserDetailsService;
    private final JWTUtil jwtUtil;
    private final JWTTokenBlackList jwtTokenBlackList;
    private final SecurityUtils securityUtils;

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
                    token = null;
                }
            }
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtTokenBlackList.findByToken(token).isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        userDetails = (BookStoreUserDetails) bookStoreUserDetailsService.loadUserByUsernameFromJWT(username);

        if (!jwtUtil.validateToken(token, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        securityUtils.fullyAuthenticated(request, response, userDetails, filterChain);
    }
}
