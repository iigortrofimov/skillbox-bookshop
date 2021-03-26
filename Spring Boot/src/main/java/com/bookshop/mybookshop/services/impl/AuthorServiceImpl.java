package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.AuthorRepository;
import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> receiveAllAuthors() {
        return authorRepository.findAll();
    }

    public Map<String, List<Author>> receiveAuthorsMap() {
        return authorRepository.findAll().stream().collect(Collectors.groupingBy(a -> a.getLastName().substring(0, 1)));
    }

    public Author receiveAuthorById(Integer authorId) {
        return authorRepository.getOne(authorId);
    }

    public void setModelWithAuthorInfoByAuthorFullName(String authorFullName, Model model) {
        String[] fullName = authorFullName.split("_");
        String lastName = fullName[0];
        String firstName = fullName[1];
        Author author = authorRepository.findByLastNameAndFirstName(lastName, firstName);
        model.addAttribute("specificAuthor", author);
        model.addAttribute("authorFullName", author.getLastName() + " " + author.getFirstName());
        //description
        String fullDescription = author.getDescription();
        if (fullDescription.length() < 1000) {
            model.addAttribute("firstPartDescription", fullDescription);
            model.addAttribute("secondPartDescription", null);
        } else {
            model.addAttribute("firstPartDescription", fullDescription.substring(0, 999));
            model.addAttribute("secondPartDescription", fullDescription.substring(999, fullDescription.length() - 1));
        }
    }
}
