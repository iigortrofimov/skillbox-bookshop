package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.Author;
import com.bookshop.mybookshop.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class BookService {

    private final JdbcTemplate jdbcTemplate;
    private AuthorService authorService;


    public List<Book> receiveAllBooks() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", this::mapBook);
        return new ArrayList<>(books);
    }

    public List<Book> receiveBooksByTitleFilter(String title) {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE title=?", this::mapBook, title);
        return new ArrayList<>(books);
    }

    private Book mapBook(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getString("price"));
        book.setPriceOld(resultSet.getString("priceOld"));

        Integer authorId = jdbcTemplate.queryForObject(
                "SELECT author_id FROM authors_books WHERE book_id=?", Integer.class, book.getId());

        Author authorFromDB = authorService.receiveAuthorById(authorId);

        book.setAuthor(authorFromDB);
        return book;
    }

}
