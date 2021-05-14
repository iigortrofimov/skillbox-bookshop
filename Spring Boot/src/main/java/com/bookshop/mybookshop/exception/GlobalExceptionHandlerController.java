package com.bookshop.mybookshop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(UsernameNotFoundException ex, RedirectAttributes redirectAttributes) {
        log.warn(ex.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("UsernameNotFoundError", ex);
        return "redirect:/signin";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException ex, RedirectAttributes redirectAttributes) {
        log.warn(ex.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("UsernameNotFoundError", ex);
        redirectAttributes.addFlashAttribute("IncorrectPasswordError", ex);
        return "redirect:/signin";
    }
}
