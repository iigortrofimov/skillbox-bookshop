package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private BookService bookService;

    @GetMapping("{title}")
    public String searchByTitle(@PathVariable("title") String title, Model model) {
        model.addAttribute("searchData", bookService.receiveBooksByTitleFilter(title));
        return "search";
    }

}
