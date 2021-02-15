package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.domain.Book;
import com.bookshop.mybookshop.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
@Api(description = "Book data API")
public class BookRestApiController {

    private final BookService bookService;

    @ApiOperation("Get books from BookShop by author's first name")
    @GetMapping("/by-author")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("firstName") String firstName) {
        return ResponseEntity.ok(bookService.receiveBooksByAuthorName(firstName));
    }

    @ApiOperation("Get books from BookShop by title")
    @GetMapping("/by-title")
    public ResponseEntity<List<Book>> booksByTitle(@RequestParam("title") String title) {
        return ResponseEntity.ok(bookService.receiveBooksByTitle(title));
    }

    @ApiOperation("Get books from BookShop by price")
    @GetMapping("/by-price")
    public ResponseEntity<List<Book>> booksByPrice(@RequestParam("price") Integer price) {
        return ResponseEntity.ok(bookService.receiveBooksByPrice(price));
    }

    @ApiOperation("Get books from BookShop by price range from min to max price")
    @GetMapping("/with-price-between")
    public ResponseEntity<List<Book>> booksWithPriceBetween(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
        return ResponseEntity.ok(bookService.receiveBooksWithPriceBetween(min, max));
    }

    @ApiOperation("Get books from BookShop with Max discount")
    @GetMapping("/with-max-discount")
    public ResponseEntity<List<Book>> booksWithMaxDiscount() {
        return ResponseEntity.ok(bookService.receiveBooksWithMaxDiscount());
    }

    @ApiOperation("Get bestsellers books from BookShop")
    @GetMapping("/bestsellers")
    public ResponseEntity<List<Book>> bestsellers() {
        return ResponseEntity.ok(bookService.receiveBestSellers());
    }

}
