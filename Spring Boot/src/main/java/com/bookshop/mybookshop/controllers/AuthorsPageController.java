package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorsPageController {

    private final AuthorService authorService;

    @GetMapping("/index.html")
    public String authorsPage(Model model) {
        model.addAttribute("authorsData", authorService.getAuthorsData());
        return "/authors/index";
    }

}
