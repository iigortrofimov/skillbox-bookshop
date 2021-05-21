package com.bookshop.mybookshop.security;

import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookStoreUserDetailsServiceTests {

    private final BookStoreUserDetailsService bookStoreUserDetailsService;

    @Autowired
    BookStoreUserDetailsServiceTests(BookStoreUserDetailsService bookStoreUserDetailsService) {
        this.bookStoreUserDetailsService = bookStoreUserDetailsService;
    }

    @Test
    void loadUserByUsername() {
        final String userName = "test@mail.com";
        UserDetails userDetails = bookStoreUserDetailsService.loadUserByUsername(userName);
        assertNotNull(userDetails);
        Assertions.assertEquals(userName, userDetails.getUsername());
        Assertions.assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                userDetails.getAuthorities());
    }

    @Test
    void processOAuthPostLogin() {
        final String email = "newEmail@mail.com";
        final String name = "Tom";
        BookStoreUser bookStoreUser = bookStoreUserDetailsService.processOAuthPostLogin(email, name);
        assertNotNull(bookStoreUser);
        Assertions.assertEquals(name, bookStoreUser.getName());
        Assertions.assertEquals(email, bookStoreUser.getEmail());
        Assertions.assertEquals(Provider.GOOGLE, bookStoreUser.getProvider());
    }
}