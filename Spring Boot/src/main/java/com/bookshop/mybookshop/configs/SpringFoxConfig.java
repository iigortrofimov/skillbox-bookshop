package com.bookshop.mybookshop.configs;


import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
//TODO убрать закомиченый код
@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //  .apis(RequestHandlerSelectors.basePackage("com.bookshop.mybookshop.controllers"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //.paths(PathSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "BookShop API",
                "Some descriptions",
                "1.0",
                "http://site.com",
                new Contact("FIO", "http://contact.org", "email@gnail.com"),
                "Licence",
                "http://licences.com",
                new ArrayList<>());
    }
}
