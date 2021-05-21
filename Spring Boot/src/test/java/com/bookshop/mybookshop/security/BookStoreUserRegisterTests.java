package com.bookshop.mybookshop.security;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookStoreUserRegisterTests {

    private final BookStoreUserRegister bookStoreUserRegister;
    private final PasswordEncoder passwordEncoder;
    private RegistrationForm registrationForm;

    @MockBean
    private BookStoreUserRepository bookStoreUserRepositoryMock;

    @Autowired
    BookStoreUserRegisterTests(BookStoreUserRegister bookStoreUserRegister, PasswordEncoder passwordEncoder) {
        this.bookStoreUserRegister = bookStoreUserRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setEmail("testtest@mail.com");
        registrationForm.setName("Bill");
        registrationForm.setPassword("12345678");
        registrationForm.setPhone("+79111111111");
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void registerNewUser() {
        BookStoreUser returnedBookStoreUser = new BookStoreUser();
        returnedBookStoreUser.setName(registrationForm.getName());
        returnedBookStoreUser.setEmail(registrationForm.getEmail());
        returnedBookStoreUser.setPhone(registrationForm.getPhone());
        returnedBookStoreUser.setPassword(passwordEncoder.encode(registrationForm.getPassword()));

        Mockito.doReturn(returnedBookStoreUser).when(bookStoreUserRepositoryMock).save(Mockito.any(BookStoreUser.class));

        BookStoreUser newUser = bookStoreUserRegister.registerNewUser(registrationForm);
        assertNotNull(newUser);
        assertTrue(CoreMatchers.is(newUser.getPhone()).matches(registrationForm.getPhone()));
        assertTrue(CoreMatchers.is(newUser.getEmail()).matches(registrationForm.getEmail()));
        assertTrue(CoreMatchers.is(newUser.getName()).matches(registrationForm.getName()));
        assertTrue(passwordEncoder.matches(registrationForm.getPassword(), newUser.getPassword()));

        Mockito.verify(bookStoreUserRepositoryMock, Mockito.times(1)).save(Mockito.any(BookStoreUser.class));
    }

    @Test
    void registerNewUserFail() {
        Mockito.doReturn(new BookStoreUser()).when(bookStoreUserRepositoryMock).findByEmail(registrationForm.getName());
        BookStoreUser newUser = bookStoreUserRegister.registerNewUser(registrationForm);
        assertNull(newUser);
    }
}