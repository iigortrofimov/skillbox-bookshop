package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.dao.GenreRepository;
import com.bookshop.mybookshop.dao.TagRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.BookTag;
import com.bookshop.mybookshop.domain.book.Genre;
import com.bookshop.mybookshop.dto.GenreDto;
import com.bookshop.mybookshop.services.BookService;
import com.bookshop.mybookshop.services.GenreService;
import com.bookshop.mybookshop.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    private BookRepository bookRepository;
    private BookService bookService;
    private TagService tagService;
    private TagRepository tagRepository;
    private GenreRepository genreRepository;
    private GenreService genreService;

    @GetMapping("/test")
    public List<Book> test() {
        return bookRepository.findAll();
    }

    @GetMapping("/test2")
    public List<Book> test2() {
        return bookRepository.findByOrderByPopularityIndexDesc();
    }

    @GetMapping("/test3")
    public Page<Book> test3(@RequestParam("page") Integer page) {
        return bookRepository.findByOrderByPopularityIndexDesc(PageRequest.of(page, 1));
    }

    @GetMapping("/testtag")
    public List<BookTag> testTag1() {
        return tagRepository.findAll();
    }

    @GetMapping("/testxs")
    public List<BookTag> testTag2() {
        return tagService.receiveBookTagsWithXsTag();
    }

    @GetMapping("/testsm")
    public List<BookTag> testTag3() {
        return tagService.receiveBookTagsWithSmTag();
    }

    @GetMapping("/testgenre")
    public List<Genre> testGenre1() {
        return genreRepository.findAll();
    }

    @GetMapping("/testgenredto")
    public List<GenreDto> testGenreDto1() {
        return genreService.receiveAllGenresDtoSortedList();
    }

    @GetMapping("/testgenrefulllist")
    public Page<Book> testGenreFullList1(@RequestParam("genre") String genre) {
        return bookService.receivePageOfBooksWithSpecificGenre(genre, 0, 6);
    }
}
