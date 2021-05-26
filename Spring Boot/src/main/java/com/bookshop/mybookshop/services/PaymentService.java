package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.book.Book;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface PaymentService {
    String getPaymentUrl(List<Book> booksFromCookieSlugs) throws NoSuchAlgorithmException;
}
