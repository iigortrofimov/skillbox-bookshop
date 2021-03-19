package com.bookshop.mybookshop;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class MyBookShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBookShopApplication.class, args);
    }

}
