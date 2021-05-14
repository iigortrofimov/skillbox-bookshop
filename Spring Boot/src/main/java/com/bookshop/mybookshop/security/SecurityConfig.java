package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.security.jwt.JWTRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BookStoreUserDetailsService bookStoreUserDetailsService;
    private final JWTRequestFilter filter;
    private final CustomOAuth2UserService oauthUserService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customUnauthorizedHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    //Used for REST
/*    private final RestAuthenticationEntryPoint unauthorizedHandler;
    private final RestAccessDeniedHandler accessDeniedHandler;
    private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;*/

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // With UserDetailsService
        /*auth.userDetailsService(bookStoreUserDetailsService)
               .passwordEncoder(getPasswordEncoder());*/
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/my", "/profile").authenticated()//.hasRole("USER")
                .antMatchers("/**", "/login", "/oauth/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/signin").failureForwardUrl("/signin")
                .failureHandler(customAuthenticationFailureHandler)
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/signin").deleteCookies("token").addLogoutHandler(customLogoutHandler)
                .and().oauth2Login().loginPage("/signin").userInfoEndpoint().userService(oauthUserService)
                .and()
                .successHandler((request, response, authentication) -> {
                    CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                    bookStoreUserDetailsService.processOAuthPostLogin(oauthUser.getEmail(), oauthUser.getName());
                    response.sendRedirect("/my");
                })
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(customUnauthorizedHandler);

        http
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
