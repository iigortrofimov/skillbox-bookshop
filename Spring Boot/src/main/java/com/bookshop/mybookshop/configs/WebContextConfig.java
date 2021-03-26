package com.bookshop.mybookshop.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebContextConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/genres").setViewName("genres/index.html");
        registry.addViewController("/popular").setViewName("books/popular.html");
        registry.addViewController("/postponed.html").setViewName("postponed.html");
        registry.addViewController("/cart.html").setViewName("cart.html");
        registry.addViewController("/signin.html").setViewName("signin.html");
        registry.addViewController("/genres/slug.html").setViewName("genres/slug.html");
        registry.addViewController("/authors/slug.html").setViewName("authors/slug.html");
        registry.addViewController("/profile.html").setViewName("profile.html");
        registry.addViewController("/faq.html").setViewName("faq.html");
        registry.addViewController("/about.html").setViewName("about.html");
        registry.addViewController("/documents.html").setViewName("documents.html");
        registry.addViewController("/contacts.html").setViewName("contacts.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
