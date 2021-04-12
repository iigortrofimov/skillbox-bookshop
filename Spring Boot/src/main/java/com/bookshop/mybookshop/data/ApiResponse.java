package com.bookshop.mybookshop.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;

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
}
