package com.bookshop.mybookshop.data;

import com.bookshop.mybookshop.controllers.rest.AuthorRestApiController;
import com.bookshop.mybookshop.controllers.rest.BookRestApiController;
import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.domain.book.Book;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ApiResponse.
 *
 * @param <T>
 */
@Data
public class ApiResponse<T> {

    /**
     * HttpStatus.
     */
    private HttpStatus httpStatus;

    /**
     * TimeStamp.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    /**
     * Message.
     */
    private String message;

    /**
     * DebugMessage.
     */
    private String debugMessage;

    /**
     * Collection with data.
     */
    private Collection<T> data;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(HttpStatus httpStatus, String message, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public static ApiResponse<Book> setBookApiResponse(List<Book> books) {
        List<Book> bookWithRef = books.stream()
                .map(book -> book.add(
                        linkTo(
                                methodOn(BookRestApiController.class)
                                        .bookById(book.getId())).withSelfRel(),
                        linkTo(
                                methodOn(AuthorRestApiController.class)
                                        .authorById(book.getAuthors().get(0).getId())).withRel("author_link")))
                .peek(book -> book.getAuthors()
                        .forEach(author -> author.add(
                                linkTo(
                                        methodOn(AuthorRestApiController.class)
                                                .authorById(author.getId())).withSelfRel())))
                .collect(Collectors.toList());
        return setResponseFields(bookWithRef);
    }

    public static ApiResponse<Author> setAuthorApiResponse(List<Author> authors) {
        List<Author> authorsWithRef = authors.stream()
                .map(author -> author.add(linkTo(
                        methodOn(AuthorRestApiController.class)
                                .authorById(author.getId())).withSelfRel()))
                .peek(author -> author.getBooks().forEach(book -> book.add(
                        linkTo(
                                methodOn(BookRestApiController.class)
                                        .bookById(book.getId())).withSelfRel())))
                .collect(Collectors.toList());
        return setResponseFields(authorsWithRef);
    }

    private static <D> ApiResponse<D> setResponseFields(List<D> dataWithRef) {
        ApiResponse<D> response = new ApiResponse<>();
        response.setData(dataWithRef);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + dataWithRef.size() + " elements");
        response.setHttpStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        return response;
    }
}
