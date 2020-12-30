package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.entity.Book;
import org.example.app.exceptions.IOFileException;
import org.example.app.services.BookService;
import org.example.web.dto.BookToDelete;
import org.example.web.dto.BookToFilter;
import org.example.web.dto.BookToSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        model.addAttribute("bookToSave", new BookToSave());
        model.addAttribute("bookToDelete", new BookToDelete());
        model.addAttribute("bookToFilter", new BookToFilter());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("fileList", fileList());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid BookToSave bookToSave, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookToSave", bookToSave);
            model.addAttribute("bookToDelete", new BookToDelete());
            model.addAttribute("bookToFilter", new BookToFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileList());
            return "book_shelf";
        } else {
            bookService.saveBook(new Book(bookToSave));
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookToDelete bookToDelete, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookToSave", new BookToSave());
            model.addAttribute("bookToDelete", bookToDelete);
            model.addAttribute("bookToFilter", new BookToFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileList());
            return "book_shelf";
        } else {
            Optional.ofNullable(bookToDelete.getId()).ifPresent(id -> bookService.removeBookById(bookToDelete.getId()));
            Optional.ofNullable(bookToDelete.getAuthorName()).ifPresent(name -> bookService.removeAllBooksByAuthorName(name));
            Optional.ofNullable(bookToDelete.getTitle()).ifPresent(bookTitle -> bookService.removeBooksByTitle(bookTitle));
            Optional.ofNullable(bookToDelete.getSize()).ifPresent(bookSize -> bookService.removeBooksBySize(bookSize));
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/filter")
    public String filterBooks(@Valid BookToFilter bookToFilter, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookToFilter", bookToFilter);
            model.addAttribute("bookToSave", new BookToSave());
            model.addAttribute("bookToDelete", new BookToDelete());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList", fileList());
            return "book_shelf";
        } else {
            String authorNameFilter = bookToFilter.getAuthorName();
            String bookTitleFilter = bookToFilter.getTitle();
            Integer bookSizeFilter = bookToFilter.getSize();
            if (StringUtils.isEmpty(authorNameFilter) && StringUtils.isEmpty(bookTitleFilter)
                    && bookSizeFilter == null) {
                return "redirect:/books/shelf";
            }
            List<Book> filteredBooks = bookService.filterByParameters(authorNameFilter, bookTitleFilter, bookSizeFilter);
            model.addAttribute("bookToSave", new BookToSave());
            model.addAttribute("bookToDelete", new BookToDelete());
            model.addAttribute("bookToFilter", new BookToFilter());
            model.addAttribute("bookList", filteredBooks);
            model.addAttribute("fileList", fileList());
            return "book_shelf";
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty()) {
            return "redirect:/books/shelf";
        }
        String name = multipartFile.getOriginalFilename();
        byte[] bytes = multipartFile.getBytes();
        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_resources");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("new file's saved by path: " + serverFile.getAbsolutePath());
        return "redirect:/books/shelf";
    }

    @PostMapping("/download")
    public void download(@RequestParam String fileName, HttpServletResponse response) throws IOFileException {
        String rootPath = System.getProperty("catalina.home");
        File fileToDownload = new File(rootPath + File.separator + "external_resources" + File.separator + fileName);
        try (InputStream inputStream = new FileInputStream(fileToDownload)) {
            try {
                MediaType mediaType = MediaTypeFactory.getMediaType(fileToDownload.getAbsolutePath())
                        .orElse(MediaType.APPLICATION_OCTET_STREAM);
                response.setContentType(mediaType.getType());
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName.replace(" ", "_"));
                FileCopyUtils.copy(inputStream, response.getOutputStream());
            } catch (IOException e) {
                logger.info("IOError writing file to output stream", e);
                throw new IOFileException("IO Error: writing file to output stream");
            }
        } catch (IOException e) {
            logger.info(String.format("InputStream IOError: File: %s doesn't exist", fileName), e);
            throw new IOFileException(String.format("InputStream IO Error: File  %s doesn't exist", fileName));
        }
    }

    @ExceptionHandler(IOFileException.class)
    public String IOErrorHandler(Model model, IOFileException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/io_error";
    }

    private List<String> fileList() {
        String rootPath = System.getProperty("catalina.home");
        File downloadDir = new File(rootPath + File.separator + "external_resources");
        if (downloadDir.exists()) {
            return Arrays.asList(Objects.requireNonNull(downloadDir.list()));
        }
        return new ArrayList<>();
    }

}