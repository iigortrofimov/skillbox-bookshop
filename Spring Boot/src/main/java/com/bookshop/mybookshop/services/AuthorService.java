package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {

    private final JdbcTemplate jdbcTemplate;

    public List<Author> getAuthorsData() {
        List<Author> authors = jdbcTemplate.query("SELECT * from authors", (ResultSet resultSet, int row) -> {
            Author author = new Author();
            author.setFirstName(resultSet.getString("firstName"));
            author.setLastName(resultSet.getString("lastName"));
            return author;
        });
        return new ArrayList<>(authors);
    }
}
