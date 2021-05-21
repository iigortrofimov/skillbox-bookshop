package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.author.Author;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AuthorRepositoryTests {

    private final AuthorRepository authorRepository;

    @Autowired
    AuthorRepositoryTests(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    public void findByLastNameAndFirstName() {
        final String firstName = "Elvina";
        final String lastName = "Lecointe";

        Author authorByLastNameAndFirstName = authorRepository.findByLastNameAndFirstName(lastName, firstName);
        assertNotNull(authorByLastNameAndFirstName);
        assertEquals(authorByLastNameAndFirstName.getFirstName(), firstName);
        assertEquals(authorByLastNameAndFirstName.getLastName(), lastName);
    }
}