package com.bookshop.mybookshop.services;

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
            book.setAuthor(resultSet.getString("author"));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getString("price"));
            book.setPriceOld(resultSet.getString("priceOld"));
            return book;
        });
        return new ArrayList<>(books);
    }
}
