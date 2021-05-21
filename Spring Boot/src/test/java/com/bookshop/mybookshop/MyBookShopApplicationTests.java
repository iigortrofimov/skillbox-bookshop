package com.bookshop.mybookshop;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyBookShopApplicationTests {

    @Value("${auth.secret}")
    String authSecret;

    private final MyBookShopApplication application;

    @Autowired
    MyBookShopApplicationTests(MyBookShopApplication application) {
        this.application = application;
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(application);
    }

    @Test
    void verifyAuthSecret() {
        MatcherAssert.assertThat(authSecret, Matchers.containsString("box"));
    }
}
