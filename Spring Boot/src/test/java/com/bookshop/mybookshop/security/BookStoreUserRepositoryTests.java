package com.bookshop.mybookshop.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookStoreUserRepositoryTests {

    private final BookStoreUserRepository bookStoreUserRepository;

    @Autowired
    BookStoreUserRepositoryTests(BookStoreUserRepository bookStoreUserRepository) {
        this.bookStoreUserRepository = bookStoreUserRepository;
    }

    @Test
    public void testAddNewUser() {
        BookStoreUser bookStoreUser = new BookStoreUser();
        bookStoreUser.setEmail("test3@mail.com");
        bookStoreUser.setName("John");
        bookStoreUser.setPassword("12345678");
        bookStoreUser.setPhone("+79111111111");

        assertNotNull(bookStoreUserRepository.save(bookStoreUser));
    }
}