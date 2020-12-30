package org.example.app.services;

import org.example.app.dao.ProjectRepository;
import org.example.app.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void saveBook(Book book) {
        if (StringUtils.hasText(book.getAuthor()) || StringUtils.hasText(book.getTitle()) || book.getSize() != null) {
            bookRepo.store(book);
        }
    }

    public void removeBookById(Integer bookIdToRemove) {
        bookRepo.removeItemById(bookIdToRemove);
    }

    public void removeAllBooksByAuthorName(String authorName) {
        bookRepo.retrieveAll().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(authorName))
                .forEach(book -> removeBookById(book.getId()));
    }

    public void removeBooksByTitle(String bookTitle) {
        bookRepo.retrieveAll().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle))
                .forEach(book -> removeBookById(book.getId()));
    }

    public void removeBooksBySize(Integer bookSize) {
        bookRepo.retrieveAll().stream()
                .filter(book -> book.getSize().equals(bookSize))
                .forEach(book -> removeBookById(book.getId()));
    }

    public List<Book> filterByParameters(String authorNameFilter, String bookTitleFilter, Integer bookSizeFilter) {
        return bookRepo.retrieveAll().stream()
                .filter(book -> {
                    if (StringUtils.hasText(authorNameFilter)) {
                        return StringUtils.hasText(authorNameFilter) &&
                                book.getAuthor().toLowerCase().matches("[a-zA-Zа-яА-Я\\s]*"
                                        + authorNameFilter.toLowerCase() + "[a-zA-Zа-яА-Я\\s]*");
                    }
                    return true;
                })
                .filter(book -> {
                    if (StringUtils.hasText(bookTitleFilter)) {
                        return book.getTitle().toLowerCase().matches(".*" + bookTitleFilter.toLowerCase() + ".*");
                    }
                    return true;
                })
                .filter(book -> {
                    if (bookSizeFilter != null) {
                        return (book.getSize() <= bookSizeFilter + 100 && book.getSize() >= bookSizeFilter - 100);
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

}
