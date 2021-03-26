package com.bookshop.mybookshop.controllers.rest;

import com.bookshop.mybookshop.dto.BooksPageDto;
import com.bookshop.mybookshop.dto.DateTimeDto;
import com.bookshop.mybookshop.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookPagesController {

    private final BookService bookService;

    @GetMapping("/recommended")
    @ResponseBody
    public BooksPageDto receiveRecommendedBooksPage(@RequestParam("offset") Integer offset,
                                                    @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.receivePageOfRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping("/recent")
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

    @GetMapping("/popular")
    @ResponseBody
    public BooksPageDto receivePopularBooksPage(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.receivePageOfPopularBooks(offset, limit).getContent());
    }

    @GetMapping("/tag/{tagName}")
    public BooksPageDto receiveBookPageWithSpecificTag(@PathVariable(value = "tagName") String tagName,
                                                       @RequestParam("offset") Integer offset,
                                                       @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.receivePageOfBooksWithSpecificTag(tagName, offset, limit).getContent());
    }

    @GetMapping("/genre/{genreName}")
    public BooksPageDto receiveBookPageWithSpecificGenre(@PathVariable(value = "genreName") String genreName,
                                                         @RequestParam("offset") Integer offset,
                                                         @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.receivePageOfBooksWithSpecificGenre(genreName, offset, limit).getContent());
    }

    @GetMapping("/author/{authorFullName}")
    public BooksPageDto receiveBookPageWithSpecificAuthor(@PathVariable(value = "authorFullName") String authorFullName,
                                                          @RequestParam("offset") Integer offset,
                                                          @RequestParam("limit") Integer limit) {
        String[] fullName = authorFullName.split("_");
        String lastName = fullName[0];
        String firstName = fullName[1];
        return new BooksPageDto(bookService.receivePageOfBooksWithSpecificAuthor(firstName, lastName, offset, limit).getContent());
    }
}
