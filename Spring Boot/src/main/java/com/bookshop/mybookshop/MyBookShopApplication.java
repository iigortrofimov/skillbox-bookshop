package com.bookshop.mybookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.bookshop.mybookshop")
public class MyBookShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBookShopApplication.class, args);
    }

}
