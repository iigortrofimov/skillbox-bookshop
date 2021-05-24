package com.bookshop.mybookshop.controllers.rest;

import com.bookshop.mybookshop.data.ApiResponse;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.exception.BookstoreApiWrongParameterException;
import com.bookshop.mybookshop.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
@Api(description = "Book data API")
public class BookRestApiController {

    private final BookService bookService;

    @ApiOperation("Get book from BookShop by id")
    @GetMapping("/by-id")
    public ApiResponse<Book> bookById(@RequestParam("id") Integer id) {
        Book book = bookService.receiveBookById(id);
        return ApiResponse.setBookApiResponse(new ArrayList<>(Collections.singletonList(book)));
    }

    @ApiOperation("Get books from BookShop by author's full name")
    @GetMapping("/by-author")
    public ApiResponse<Book> booksByAuthor(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        List<Book> books = bookService.receiveBooksWithSpecificAuthor(firstName, lastName);
        return ApiResponse.setBookApiResponse(books);
    }

    @ApiOperation("Get books from BookShop by title")
    @GetMapping("/by-title")
    public ApiResponse<Book> booksByTitle(@RequestParam("title") String title) throws BookstoreApiWrongParameterException {
        List<Book> books = bookService.receiveBooksByTitle(title);
        return ApiResponse.setBookApiResponse(books);
    }

    @ApiOperation("Get books from BookShop by price")
    @GetMapping("/by-price")
    public ApiResponse<Book> booksByPrice(@RequestParam("price") Integer price) {
        List<Book> books = bookService.receiveBooksByPrice(price);
        return ApiResponse.setBookApiResponse(books);
    }

    @ApiOperation("Get books from BookShop by price range from min to max price")
    @GetMapping("/with-price-between")
    public ApiResponse<Book> booksWithPriceBetween(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
        List<Book> books = bookService.receiveBooksWithPriceBetween(min, max);
        return ApiResponse.setBookApiResponse(books);
    }

    @ApiOperation("Get books from BookShop with Max discount")
    @GetMapping("/with-max-discount")
    public ApiResponse<Book> booksWithMaxDiscount() {
        List<Book> books = bookService.receiveBooksWithMaxDiscount();
        return ApiResponse.setBookApiResponse(books);
    }

    @ApiOperation("Get bestsellers books from BookShop")
    @GetMapping("/bestsellers")
    public ApiResponse<Book> bestsellers() {
        return ApiResponse.setBookApiResponse(bookService.receiveBestSellers());
    }
}
