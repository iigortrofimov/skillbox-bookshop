package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.book.Book;
import java.util.List;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRepositoryTests {

    private final BookRepository bookRepository;

    @Autowired
    BookRepositoryTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    public void findByAuthorsFirstName() {
        final String query = "Evin";
        List<Book> bookListByAuthorsFirstName = bookRepository.findByAuthorsFirstName(query);
        assertNotNull(bookListByAuthorsFirstName);
        assertFalse(bookListByAuthorsFirstName.isEmpty());

        for (Book book : bookListByAuthorsFirstName) {
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString() + " author: " + book.authorsFullName());
            assertTrue(book.authorsFullName().contains(query));
        }
    }

    @Test
    public void findByIsBestsellerTrues() {
        List<Book> bestsellerBooks = bookRepository.findByIsBestsellerTrue();
        assertNotNull(bestsellerBooks);
        assertFalse(bestsellerBooks.isEmpty());
        assertThat(bestsellerBooks.size()).isGreaterThan(1);
    }

    @Test
    public void findByTitleContaining() {
        final String query = "Whity";
        List<Book> bookListByTitleContaining = bookRepository.findByTitleContaining(query);
        assertNotNull(bookListByTitleContaining);
        assertFalse(bookListByTitleContaining.isEmpty());

        for (Book book : bookListByTitleContaining) {
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertTrue(book.getTitle().contains(query));
        }
    }
}