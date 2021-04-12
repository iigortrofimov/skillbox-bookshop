package com.bookshop.mybookshop.controllers.rest;

import com.bookshop.mybookshop.data.ApiResponse;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.exception.BookstoreApiWrongParameterException;
import com.bookshop.mybookshop.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    public ResponseEntity<ApiResponse<Book>> booksByTitle(@RequestParam("title") String title) throws BookstoreApiWrongParameterException {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> books = bookService.receiveBooksByTitle(title);
        response.setData(books);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + books.size() + " elements");
        response.setHttpStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception ex) {
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Missing required parameters", ex),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception ex) {
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Bad parameters", ex),
                HttpStatus.BAD_REQUEST);
    }
}
