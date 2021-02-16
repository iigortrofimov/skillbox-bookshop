package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.domain.Book;
import com.bookshop.mybookshop.dto.BooksPageDto;
import com.bookshop.mybookshop.dto.DateTimeDto;
import com.bookshop.mybookshop.dto.SearchWordDto;
import com.bookshop.mybookshop.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
public class MainPageController {

    private final BookService bookService;

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.receivePageOfRecommendedBooks(0, 6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.receivePageOfRecentBooks(0, 6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return bookService.receivePageOfPopularBooks(0, 6).getContent();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping("/")

    public String mainPage() {
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto receiveRecommendedBooksPage(@RequestParam("offset") Integer offset,
                                                    @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.receivePageOfRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto receiveRecentBooksPage(@RequestParam("offset") Integer offset,
                                               @RequestParam("limit") Integer limit,
                                               @RequestParam(value = "from", required = false) DateTimeDto from,
                                               @RequestParam(value = "to", required = false) DateTimeDto to) {
        if (from != null && to != null) {
            return new BooksPageDto(bookService.receivePageOfRecentBooks(offset, limit, from.getDateTime(), to.getDateTime()).getContent());
        }
        return new BooksPageDto(bookService.receivePageOfRecentBooks(offset, limit).getContent());
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto receivePopularBooksPage(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.receivePageOfPopularBooks(offset, limit).getContent());
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String searchResult(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                               Model model) {
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResults",
                bookService.receivePageOfSearchResultBooks(searchWordDto.getContent(), 0, 20).getContent());
        return "search/index";
    }

    @GetMapping(value = "/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto receiveNextSearchBookPage(@PathVariable("searchWord") SearchWordDto searchWordDto,
                                                  @RequestParam("offset") Integer offset,
                                                  @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.receivePageOfSearchResultBooks(searchWordDto.getContent(), offset, limit).getContent());
    }

    @GetMapping("/recent")
    public String recentBooksPage() {
        return "/books/recent";
    }

    @GetMapping("/popular")
    public String popularBooksPage() {
        return "/books/popular";
    }

}
