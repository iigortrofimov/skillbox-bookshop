package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.Book;
import com.bookshop.mybookshop.dto.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {

    private BookRepository bookRepository;

    public List<Book> receiveAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> receiveBooksByTitleFilter(String title) {
        return bookRepository.findBooksByTitle(title);
    }

}
