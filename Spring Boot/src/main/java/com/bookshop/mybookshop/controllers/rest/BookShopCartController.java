package com.bookshop.mybookshop.controllers.rest;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.dto.SearchWordDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class BookShopCartController {

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    private final BookRepository bookRepository;

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(name = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1)
                    : cartContents;
            String[] cookiesSlug = cartContents.split("/");
            List<Book> booksFromCookiesSlugs = bookRepository.findBooksBySlugIn(cookiesSlug);
            model.addAttribute("bookCart", booksFromCookiesSlugs);
        }
        return "cart";
    }


    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable String slug, @CookieValue(name = "cartContents", required = false)
            String cartContents, HttpServletResponse response, Model model) {
        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/book");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie newCookie = new Cookie("cartContents", stringJoiner.toString());
            newCookie.setPath("/book");
            response.addCookie(newCookie);
            model.addAttribute("isCartEmpty", false);
        }
        return "redirect:/book/" + slug;
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartByRequest(@PathVariable("slug") String slug, Model model, @CookieValue(name = "cartContents", required = false)
            String cartContents, HttpServletResponse response) {
        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/book");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }
        return "redirect:/book/cart";
    }
}
