package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

    private final JdbcTemplate jdbcTemplate;

    public List<Author> receiveAllAuthors() {
        List<Author> authors = jdbcTemplate.query("SELECT * from authors", AuthorService::mapAuthor);
        return new ArrayList<>(authors);
    }

    public Map<String, List<Author>> receiveAuthorsMap() {
        List<Author> authors = jdbcTemplate.query("SELECT * from authors", AuthorService::mapAuthor);
        return authors.stream().collect(Collectors.groupingBy(a -> a.getLastName().substring(0, 1)));
    }

    static Author mapAuthor(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setFirstName(rs.getString("firstName"));
        author.setLastName(rs.getString("lastName"));
        return author;
    }

    Author receiveAuthorById(Integer authorId) {
        return jdbcTemplate.queryForObject("SELECT * from authors where id=?",
                AuthorService::mapAuthor, authorId);
    }

}
