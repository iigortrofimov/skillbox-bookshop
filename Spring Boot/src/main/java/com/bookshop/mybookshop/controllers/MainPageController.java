package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookshop")
@AllArgsConstructor
public class MainPageController {

    private final BookService bookService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("booksData", bookService.getBooksData());
        return "index";
    }

}
