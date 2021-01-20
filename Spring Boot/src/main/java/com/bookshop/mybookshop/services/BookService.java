package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.Author;
import com.bookshop.mybookshop.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class BookService {

    private final JdbcTemplate jdbcTemplate;

    public List<Book> getBooksData() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet resultSet, int row) -> {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getString("price"));
            book.setPriceOld(resultSet.getString("priceOld"));

            Integer authorId = jdbcTemplate.queryForObject(
                    "SELECT author_id FROM authors_books WHERE book_id=?", Integer.class, book.getId());

            Author authorFromDB = jdbcTemplate.queryForObject("SELECT * from authors where id=?",
                    (ResultSet rs, int rowNum) -> {
                        Author author = new Author();
                        author.setId(authorId);
                        author.setFirstName(rs.getString("firstName"));
                        author.setLastName(rs.getString("lastName"));
                        return author;
                    }, authorId);
            book.setAuthor(authorFromDB);
            return book;
        });
        return new ArrayList<>(books);
    }
}
