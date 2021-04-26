package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.exception.BookstoreApiWrongParameterException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    List<Book> receiveAllBooks();

    List<Book> receiveBooksByAuthorName(String firstName);

    List<Book> receiveBooksByTitle(String title) throws BookstoreApiWrongParameterException;

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

    List<Book> receiveBooksWithSpecificAuthor(String firstName, String lastName);

    Book receiveBookBySlug(String slug);

    void updateBookImage(MultipartFile file, String savedPath, String slug);

    Book receiveBookById(Integer id);

    List<Book> receiveBooksBySlugIn(String[] slugs);

    void saveBook(Book book);
}
