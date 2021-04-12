package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.exception.EmptySearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {

    @ExceptionHandler(EmptySearchException.class)
    public String handleEmptySearchException(Exception ex, RedirectAttributes redirectAttributes) {
        log.warn(ex.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("searchError", ex);
        return "redirect:/";
    }
}
