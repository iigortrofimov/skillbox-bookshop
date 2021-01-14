package org.example.app.config;

import org.apache.log4j.Logger;
import org.example.app.services.UserDetailServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "org.example.app")
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = Logger.getLogger(AppContextConfig.class);
    private UserDetailServiceImpl userDetailService;
    private BCryptPasswordEncoder passwordEncoder;

    public AppSecurityConfig(UserDetailServiceImpl userDetailService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Configure http security");
        http
                .authorizeRequests()
                .antMatchers("/books/**")
                .access("hasRole('USER')")
                .antMatchers("/signup/**", "/404")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/books/shelf", true)
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .and()
                .csrf()
                .disable()
                //allow web interface H2
                .headers()
                .frameOptions()
                .disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        logger.info("configure web security");
        web.ignoring()
                .antMatchers("/css/**", "/images/**", "/console/**");
    }

}
