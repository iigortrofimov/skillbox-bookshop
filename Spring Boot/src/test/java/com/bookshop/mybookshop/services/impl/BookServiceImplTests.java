package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.dao.GenreRepository;
import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.Genre;
import com.bookshop.mybookshop.services.BookService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class BookServiceImplTests {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreRepository genreRepository;

    private final BookService bookService;

    @Autowired
    BookServiceImplTests(BookService bookService) {
        this.bookService = bookService;
    }

    @Test
    void receiveAllBooks() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setDescription("description1");

        Book book2 = new Book();
        book2.setId(2);
        book2.setDescription("description2");

        Mockito.doReturn(Arrays.asList(book1, book2)).when(bookRepository).findAll();

        List<Book> booksFromDB = bookService.receiveAllBooks();
        assertNotNull(booksFromDB);
        assertFalse(booksFromDB.isEmpty());
        assertTrue(booksFromDB.contains(book1) && booksFromDB.contains(book2));
    }

    @Test
    void receiveBooksByAuthorName() {
        Author author = new Author();
        author.setId(1);
        author.setFirstName("Tom");
        author.setLastName("Hanks");

        Book book = new Book();
        book.setId(1);
        book.setDescription("description1");
        book.setAuthors(Collections.singletonList(author));

        Mockito.doReturn(Collections.singletonList(book)).when(bookRepository).findByAuthorsFirstName("Tom");

        List<Book> booksFromDB = bookService.receiveBooksByAuthorName("Tom");
        assertNotNull(booksFromDB);
        assertFalse(booksFromDB.isEmpty());
        assertTrue(booksFromDB.contains(book));
        assertTrue(booksFromDB.get(0).authorsFullName().contains("Hanks"));
    }

    @Test
    void receiveBooksByTitle() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Title1");
        book.setDescription("description1");

        Mockito.doReturn(Collections.singletonList(book)).when(bookRepository).findByTitleContaining("Title1");

        List<Book> booksFromDB = bookService.receiveBooksByTitle("Title1");
        assertNotNull(booksFromDB);
        assertFalse(booksFromDB.isEmpty());
        assertTrue(booksFromDB.contains(book));
        assertEquals("Title1", booksFromDB.get(0).getTitle());
    }

    @Test
    void receiveBooksWithPriceBetween() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setDescription("description1");
        book1.setPrice(100);
        book1.setDiscount(30);

        Book book2 = new Book();
        book2.setId(2);
        book2.setDescription("description2");
        book2.setPrice(200);
        book2.setDiscount(25);

        Mockito.doReturn(Arrays.asList(book1, book2)).when(bookRepository).findBooksByPriceBetween(99, 299);

        List<Book> booksFromDB = bookService.receiveBooksWithPriceBetween(99, 299);
        assertNotNull(booksFromDB);
        assertFalse(booksFromDB.isEmpty());
        assertTrue(booksFromDB.contains(book1) && booksFromDB.contains(book2));
        assertTrue(booksFromDB.stream().allMatch(book -> book.getPrice() > 99 && book.getPrice() < 299));
    }

    @Test
    void receiveBookById() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Title1");
        book.setDescription("description1");

        Mockito.doReturn(Optional.of(book)).when(bookRepository).findById(1);

        Book bookFromDB = bookService.receiveBookById(1);
        assertNotNull(bookFromDB);
        assertEquals("Title1", bookFromDB.getTitle());
    }

    @Test
    void receivePageOfBooksWithSpecificGenre() {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Genre1");
        genre.setSlug("GenreSlug1");

        Genre genreLevel2 = new Genre();
        genreLevel2.setId(2);
        genreLevel2.setName("Genre2");
        genreLevel2.setSlug("GenreSlug2");
        genreLevel2.setParentId(1);

        Book book1 = new Book();
        book1.setId(1);
        book1.setDescription("description1");
        book1.setGenres(Collections.singletonList(genre));

        Book book2 = new Book();
        book2.setId(2);
        book2.setDescription("description2");
        book2.setGenres(Collections.singletonList(genreLevel2));

        Page<Book> bookPage = new PageImpl<>(Arrays.asList(book1, book2), PageRequest.of(0, 2), 2);

        Mockito.doReturn(genre).when(genreRepository).findByNameIs("Genre1");
        Mockito.doReturn(Collections.singletonList(genreLevel2)).when(genreRepository).findByParentIdIs(1);

        Mockito.doReturn(bookPage).when(bookRepository).findByGenresIdIn(Arrays.asList(genre.getId(),
                genreLevel2.getId()), PageRequest.of(0, 2));

        Page<Book> bookPageFromDB = bookService.receivePageOfBooksWithSpecificGenre("Genre1", 0, 2);
        assertNotNull(bookPageFromDB);
        assertFalse(bookPageFromDB.isEmpty());
        assertTrue(bookPageFromDB.getContent().contains(book1) && bookPageFromDB.getContent().contains(book2));
    }

    @Test
    void saveBook() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Title1");
        book.setDescription("description1");

        bookService.saveBook(book);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }
}