package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.security.jwt.JWTToken;
import com.bookshop.mybookshop.security.jwt.JWTTokenBlackList;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JWTTokenBlackList jwtTokenBlackList;

    @Value("${app.jwtToken.ttl}")
    long tokenTtl;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    JWTToken jwtToken = new JWTToken();
                    jwtToken.setToken(cookie.getValue());
                    jwtToken.setTtl(tokenTtl);
                    jwtTokenBlackList.save(jwtToken);
                }
            }
        }
    }
}
