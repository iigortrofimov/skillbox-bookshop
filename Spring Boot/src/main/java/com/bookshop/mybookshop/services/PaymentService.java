package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.dto.PaymentResponse;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;


public interface PaymentService {

    String getPaymentUrlForBooksFromCart(List<Book> booksFromCookieSlugs) throws NoSuchAlgorithmException;

    String topUpUserAccount(Double sum, Principal email) throws NoSuchAlgorithmException;

    PaymentResponse payFromUserAccount(List<Book> booksFromCookieSlugs);
}
