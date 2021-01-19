package com.bookshop.mybookshop.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebContextConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/genres/index.html").setViewName("genres/index.html");
        registry.addViewController("/index.html").setViewName("redirect:/bookshop/main");
    }

}
