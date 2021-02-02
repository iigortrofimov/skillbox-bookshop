package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.Author;
import com.bookshop.mybookshop.dto.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    public List<Author> receiveAllAuthors() {
        return authorRepository.findAll();
    }

    public Map<String, List<Author>> receiveAuthorsMap() {
        return authorRepository.findAll().stream().collect(Collectors.groupingBy(a -> a.getLastName().substring(0, 1)));
    }

    Author receiveAuthorById(Integer authorId) {
        return authorRepository.getOne(authorId);
    }

}
