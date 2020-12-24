package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") String bookIdToRemove,
                             @RequestParam(value = "authorName") String authorName,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "size") Integer size) {
        Optional.ofNullable(bookIdToRemove).ifPresent(id -> bookService.removeBookById(id));
        Optional.ofNullable(authorName).ifPresent(name -> bookService.removeAllBooksByAuthorName(name));
        Optional.ofNullable(title).ifPresent(bookTitle -> bookService.removeBooksByTitle(bookTitle));
        Optional.ofNullable(size).ifPresent(bookSize -> bookService.removeBooksBySize(bookSize));
        return "redirect:/books/shelf";
    }

    @PostMapping("/filter")
    public String filterBooks(@RequestParam(value = "authorName") String authorNameFilter,
                              @RequestParam(value = "title") String bookTitleFilter,
                              @RequestParam(value = "size") Integer bookSizeFilter,
                              Model model) {
        if (StringUtils.isEmpty(authorNameFilter) && StringUtils.isEmpty(bookTitleFilter) && bookSizeFilter == null) {
            return "redirect:/books/shelf";
        }
        List<Book> filteredBooks = bookService.filterByParameters(authorNameFilter, bookTitleFilter, bookSizeFilter);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", filteredBooks);
        return "book_shelf";
    }

}