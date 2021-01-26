package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.domain.Author;
import com.bookshop.mybookshop.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorsPageController {

    private final AuthorService authorService;

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        Map<String, List<Author>> map = authorService.receiveAuthorsMap();
        return authorService.receiveAuthorsMap();
    }

    @GetMapping()
    public String authorsPage(Model model) {
        return "/authors/index";
    }

}
