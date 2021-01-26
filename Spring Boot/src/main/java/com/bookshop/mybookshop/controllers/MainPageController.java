package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.domain.Book;
import com.bookshop.mybookshop.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
public class MainPageController {

    private final BookService bookService;

    @ModelAttribute("booksData")
    public List<Book> booksData() {
        return bookService.receiveAllBooks();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

}
