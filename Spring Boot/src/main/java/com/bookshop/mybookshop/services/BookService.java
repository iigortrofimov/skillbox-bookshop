package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.book.Book;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface BookService {

    List<Book> receiveAllBooks();

    List<Book> receiveBooksByAuthorName(String firstName);

    List<Book> receiveBooksByTitle(String title);

    List<Book> receiveBooksWithPriceBetween(Integer min, Integer max);

    List<Book> receiveBooksByPrice(Integer price);

    List<Book> receiveBooksWithMaxDiscount();

    List<Book> receiveBestSellers();

    Page<Book> receivePageOfRecommendedBooks(Integer offset, Integer limit);

    Page<Book> receivePageOfRecentBooks(Integer offset, Integer limit);

    Page<Book> receivePageOfPopularBooks(Integer offset, Integer limit);

    Page<Book> receivePageOfSearchResultBooks(String searchTitle, Integer offset, Integer limit);

    Page<Book> receivePageOfRecentBooks(Integer offset, Integer limit, LocalDateTime from, LocalDateTime to);

    Page<Book> receivePageOfBooksWithSpecificTag(String tagName, Integer offset, Integer limit);

    Page<Book> receivePageOfBooksWithSpecificGenre(String genreName, Integer offset, Integer limit);

    Page<Book> receivePageOfBooksWithSpecificAuthor(String firstName, String lastName, Integer offset, Integer limit);

}
