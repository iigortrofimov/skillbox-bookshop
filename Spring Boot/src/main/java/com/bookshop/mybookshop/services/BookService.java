package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {

    private BookRepository bookRepository;

    public List<Book> receiveAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> receiveBooksByAuthorName(String firstName) {
        return bookRepository.findByAuthorsFirstName(firstName);
    }

    public List<Book> receiveBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> receiveBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceBetween(min, max);
    }

    public List<Book> receiveBooksByPrice(Integer price) {
        return bookRepository.findBooksByPriceIs(price);
    }

    public List<Book> receiveBooksWithMaxDiscount() {
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> receiveBestSellers() {
        return bookRepository.findByIsBestsellerTrue();
    }

    public Page<Book> receivePageOfRecommendedBooks(Integer offset, Integer limit) {
        return bookRepository.findAll(PageRequest.of(offset, limit));
    }

    public Page<Book> receivePageOfRecentBooks(Integer offset, Integer limit) {
        return bookRepository.findAllByOrderByPublicationDateDesc(PageRequest.of(offset, limit));
    }

    public Page<Book> receivePageOfPopularBooks(Integer offset, Integer limit) {
        return bookRepository.findByIsBestsellerTrue(PageRequest.of(offset, limit));
    }




    public Page<Book> receivePageOfSearchResultBooks(String searchTitle, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findByTitleContaining(searchTitle, nextPage);
    }
}
