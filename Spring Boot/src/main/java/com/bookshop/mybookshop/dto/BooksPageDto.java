package com.bookshop.mybookshop.dto;

import com.bookshop.mybookshop.domain.book.Book;
import lombok.Data;

import java.util.List;

@Data
public class BooksPageDto {

    private Integer count;

    private List<Book> books;

    public BooksPageDto(List<Book> recommendedBooks) {
        this.count = recommendedBooks.size();
        books = recommendedBooks;
    }

}
