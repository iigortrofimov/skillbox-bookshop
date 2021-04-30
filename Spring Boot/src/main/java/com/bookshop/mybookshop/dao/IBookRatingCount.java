package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.book.BookRate;

public interface IBookRatingCount {

    BookRate getRate();

    Integer getBookRateCount();
}