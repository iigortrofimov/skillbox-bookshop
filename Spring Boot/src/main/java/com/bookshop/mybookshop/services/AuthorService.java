package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.author.Author;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface AuthorService {

    List<Author> receiveAllAuthors();

    Map<String, List<Author>> receiveAuthorsMap();

    Author receiveAuthorById(Integer authorId);

    void setModelWithAuthorInfoByAuthorFullName(String authorFullName, Model model);

    Author receiveAuthorByFullName(String firstName, String lastName);
}
