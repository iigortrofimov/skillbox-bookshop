package com.bookshop.mybookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.bookshop.mybookshop")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class MyBookShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBookShopApplication.class, args);
    }
}
