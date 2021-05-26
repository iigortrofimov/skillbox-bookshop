package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.BookStatus;
import com.bookshop.mybookshop.dto.BookRateValue;
import com.bookshop.mybookshop.dto.BookStatusDto;
import com.bookshop.mybookshop.dto.SearchWordDto;
import com.bookshop.mybookshop.services.BookService;
import com.bookshop.mybookshop.services.RatingService;
import com.bookshop.mybookshop.services.PaymentService;
import com.bookshop.mybookshop.util.CookieUtils;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class BookShopStatusController {

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @ModelAttribute(name = "bookPostponedList")
    public List<Book> BookPostponedList() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    private final BookService bookService;

    private final RatingService ratingService;

    private final PaymentService paymentService;

    private static final String POSTPONED_CONTENTS_COOKIE_NAME = "postponedContents";

    private static final String CART_CONTENTS_COOKIE_NAME = "cartContents";

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(name = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartContentsEmpty", true);
        } else {
            model.addAttribute("isCartContentsEmpty", false);
            String[] arrayWithBookSlugsFromCookie = CookieUtils.createArrayWithBookSlugsFromCookie(cartContents);
            List<Book> booksFromCookiesSlugs = bookService.receiveBooksBySlugIn(arrayWithBookSlugsFromCookie);
            model.addAttribute("bookCart", booksFromCookiesSlugs);
        }
        return "cart";
    }

    @GetMapping("/postponed")
    public String handlePostponedListRequest(@CookieValue(name = "postponedContents", required = false) String postponedContents,
                                             Model model) {
        if (postponedContents == null || postponedContents.equals("")) {
            model.addAttribute("isPostponedListEmpty", true);
        } else {
            model.addAttribute("isPostponedListEmpty", false);
            String[] arrayWithBookSlugsFromCookie = CookieUtils.createArrayWithBookSlugsFromCookie(postponedContents);
            List<Book> booksFromCookiesSlugs = bookService.receiveBooksBySlugIn(arrayWithBookSlugsFromCookie);
            model.addAttribute("bookPostponedList", booksFromCookiesSlugs);
        }
        return "postponed";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable String slug,
                                         @CookieValue(name = "cartContents", required = false) String cartContents,
                                         @CookieValue(name = "postponedContents", required = false) String postponedContents,
                                         HttpServletResponse response, Model model, @RequestBody BookStatusDto bookStatusDto) {
        if (bookStatusDto.getStatus().equals(BookStatus.CART.name())) {
            addBookToCookieContentBySlug(cartContents, CART_CONTENTS_COOKIE_NAME, slug, response, model);
            changeBookStatus(slug, BookStatus.CART);
        } else if (bookStatusDto.getStatus().equals(BookStatus.KEPT.name())) {
            addBookToCookieContentBySlug(postponedContents, POSTPONED_CONTENTS_COOKIE_NAME, slug, response, model);
            changeBookStatus(slug, BookStatus.KEPT);
        }
        return "redirect:/book/" + slug;
    }


    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartByRequest(@PathVariable("slug") String slug, Model model,
                                                    @CookieValue(name = "cartContents", required = false)
                                                            String cartContents, HttpServletResponse response) {
        if (cartContents != null && !cartContents.equals("")) {
            removeBookBySlugFromCookieContentAndSetModelAttr(cartContents, slug, CART_CONTENTS_COOKIE_NAME,
                    response, model, BookStatus.CART);
        } else {
            model.addAttribute("isCartContentsEmpty", true);
        }
        return "redirect:/book/cart";
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveBookFromPostponedListByRequest(@PathVariable("slug") String slug, Model model,
                                                             @CookieValue(name = "postponedContents", required = false)
                                                                     String postponedContents, HttpServletResponse response) {
        if (postponedContents != null && !postponedContents.equals("")) {
            removeBookBySlugFromCookieContentAndSetModelAttr(postponedContents, slug, POSTPONED_CONTENTS_COOKIE_NAME,
                    response, model, BookStatus.KEPT);
        } else {
            model.addAttribute("isPostponedListEmpty", true);
        }
        return "redirect:/book/postponed";
    }

    /**
     * Receives rating value by POST method
     * and saves it into {@link Book}.
     *
     * @param bookRateValue rating value.
     * @param slug          mnemonical identifier.
     * @return redirect to book page by slug.
     */
    @PostMapping("/changeBookStatus/rating/{slug}")
    public String addRatingValue(@RequestBody BookRateValue bookRateValue, @PathVariable("slug") String slug) {
        ratingService.addRateIntoOverallRating(slug, bookRateValue.getValue());
        return "redirect:/book/" + slug;
    }

    private void addBookToCookieContentBySlug(String stringFromCookie, String cookieName, String bookSlug,
                                              HttpServletResponse response, Model model) {
        if (stringFromCookie == null || stringFromCookie.equals("")) {
            setCookieAndDefineEmptyAttr(cookieName, bookSlug, response, model);
        } else if (!stringFromCookie.contains(bookSlug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(stringFromCookie).add(bookSlug);
            setCookieAndDefineEmptyAttr(cookieName, stringJoiner.toString(), response, model);
        }
    }

    private void setCookieAndDefineEmptyAttr(String cookieName, String cookieContent,
                                             HttpServletResponse response, Model model) {
        Cookie cookie = new Cookie(cookieName, cookieContent);
        cookie.setPath("/book");
        response.addCookie(cookie);
        model.addAttribute("is" + cookieName.substring(0, 1).toUpperCase() +
                cookieName.substring(1) + "Empty", false);
    }

    private void changeBookStatus(String bookSlug, BookStatus bookStatus) {
        Book bookBySlug = bookService.receiveBookBySlug(bookSlug);
        bookBySlug.getStatuses().add(bookStatus);
        bookService.saveBook(bookBySlug);
    }

    private void removeBookBySlugFromCookieContentAndSetModelAttr(String contentFromCookie, String bookSlug, String cookieName,
                                                                  HttpServletResponse response, Model model, BookStatus bookStatus) {
        ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(contentFromCookie.split("/")));
        cookieBooks.remove(bookSlug);
        Cookie cookie = new Cookie(cookieName, String.join("/", cookieBooks));
        cookie.setPath("/book");
        response.addCookie(cookie);
        model.addAttribute("is" + cookieName.substring(0, 1).toUpperCase() + cookieName.substring(1) +
                "Empty", false);

        Book book = bookService.receiveBookBySlug(bookSlug);
        book.getStatuses().remove(bookStatus);
        bookService.saveBook(book);
    }

    @GetMapping("/pay")
    public RedirectView handlePay(@CookieValue(name = "cartContents", required = false) String cartContents) throws NoSuchAlgorithmException {
        String[] cookieSlugs = CookieUtils.createArrayWithBookSlugsFromCookie(cartContents);
        List<Book> booksFromCookieSlugs = bookService.receiveBooksBySlugIn(cookieSlugs);
        String paymentUrl = paymentService.getPaymentUrl(booksFromCookieSlugs);
        return new RedirectView(paymentUrl);
    }
}
