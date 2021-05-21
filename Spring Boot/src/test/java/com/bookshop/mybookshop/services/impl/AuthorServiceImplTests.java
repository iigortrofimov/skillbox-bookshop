package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.AuthorRepository;
import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.services.AuthorService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AuthorServiceImplTests {

    @MockBean
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    @Autowired
    public AuthorServiceImplTests(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Test
    void receiveAllAuthors() {
        Author author1 = new Author();
        author1.setId(1);
        author1.setFirstName("Tom");
        author1.setLastName("Hanks");

        Author author2 = new Author();
        author2.setId(2);
        author2.setFirstName("Bill");
        author2.setLastName("Gates");

        Mockito.doReturn(Arrays.asList(author1, author2)).when(authorRepository).findAll();

        List<Author> authorsFromDb = authorService.receiveAllAuthors();
        Assertions.assertNotNull(authorsFromDb);
        Assertions.assertFalse(authorsFromDb.isEmpty());
        Assertions.assertTrue(authorsFromDb.contains(author1) && authorsFromDb.contains(author2));
    }

    @Test
    void receiveAuthorById() {
        Author author = new Author();
        author.setId(1);
        author.setFirstName("Tom");
        author.setLastName("Hanks");

        Mockito.doReturn(Optional.of(author)).when(authorRepository).findById(1);

        Author authorFromDB = authorService.receiveAuthorById(1);
        Assertions.assertNotNull(authorFromDB);
        Assertions.assertTrue(CoreMatchers.is(author.getFirstName()).matches(authorFromDB.getFirstName()));
        Assertions.assertTrue(CoreMatchers.is(author.getLastName()).matches(authorFromDB.getLastName()));
    }

    @Test
    void receiveAuthorByFullName() {
        Author author = new Author();
        author.setId(1);
        author.setFirstName("Bill");
        author.setLastName("Gates");

        Mockito.doReturn(author).when(authorRepository).findByLastNameAndFirstName("Gates", "Bill");

        Author authorFromDB = authorService.receiveAuthorByFullName("Bill", "Gates");
        Assertions.assertNotNull(authorFromDB);
        Assertions.assertTrue(CoreMatchers.is(author.getFirstName()).matches(authorFromDB.getFirstName()));
        Assertions.assertTrue(CoreMatchers.is(author.getLastName()).matches(authorFromDB.getLastName()));
    }
}