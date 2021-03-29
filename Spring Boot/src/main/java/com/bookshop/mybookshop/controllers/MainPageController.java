package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.BookTag;
import com.bookshop.mybookshop.dto.BooksPageDto;
import com.bookshop.mybookshop.dto.GenreDto;
import com.bookshop.mybookshop.dto.SearchWordDto;
import com.bookshop.mybookshop.services.AuthorService;
import com.bookshop.mybookshop.services.BookService;
import com.bookshop.mybookshop.services.GenreService;
import com.bookshop.mybookshop.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
@AllArgsConstructor
public class MainPageController {

    private final BookService bookService;
    private final TagService tagService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.receivePageOfRecommendedBooks(0, 6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.receivePageOfRecentBooks(0, 6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return bookService.receivePageOfPopularBooks(0, 6).getContent();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("booksWithXsTag")
    public List<BookTag> booksWithXsTag() {
        return tagService.receiveBookTagsWithXsTag();
    }

    @ModelAttribute("booksWithSmTag")
    public List<BookTag> booksWithSmTag() {
        return tagService.receiveBookTagsWithSmTag();
    }

    @ModelAttribute("booksWithTag")
    public List<BookTag> booksWithNormalTag() {
        return tagService.receiveBookTagsWithTag();
    }

    @ModelAttribute("booksWithMdTag")
    public List<BookTag> booksWithMdTag() {
        return tagService.receiveBookTagsWithMdTag();
    }

    @ModelAttribute("booksWithLgTag")
    public List<BookTag> booksWithLgTag() {
        return tagService.receiveBookTagsWithLgTag();
    }

    @ModelAttribute("genreList")
    public List<GenreDto> genreList() {
        return genreService.receiveAllGenresDtoSortedList();
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        return authorService.receiveAuthorsMap();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/recent")
    public String recentBooksPage() {
        return "/books/recent";
    }

    @GetMapping("/genres")
    public String genresPage() {
        return "/genres/index";
    }

    @GetMapping("/popular")
    public String popularBooksPage() {
        return "/books/popular";
    }

    @GetMapping("/authors")
    public String authorsPage() {
        return "/authors/index";
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String searchResult(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                               Model model) {
        if (searchWordDto != null) {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults",
                    bookService.receivePageOfSearchResultBooks(searchWordDto.getContent(), 0, 20).getContent());
        }
        return "search/index";
    }

    @GetMapping(value = "/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto receiveNextSearchBookPage(@PathVariable("searchWord") SearchWordDto searchWordDto,
                                                  @RequestParam("offset") Integer offset,
                                                  @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.receivePageOfSearchResultBooks(searchWordDto.getContent(), offset, limit).getContent());
    }

    @GetMapping("/tags/{tagName}")
    public String tagPage(@PathVariable("tagName") String tagName, Model model) {
        List<Book> bookListWithSpecificTag = bookService.receivePageOfBooksWithSpecificTag(tagName, 0, 20).getContent();
        model.addAttribute("booksWithSpecificTag", bookListWithSpecificTag);
        model.addAttribute("tagName", tagName);
        return "tags/index";
    }

    @GetMapping("/genres/{genreName}")
    public String genrePage(@PathVariable("genreName") String genreName, Model model) {
        List<Book> bookListWithSpecificGenre = bookService.receivePageOfBooksWithSpecificGenre(genreName, 0, 20).getContent();
        model.addAttribute("booksWithSpecificGenre", bookListWithSpecificGenre);
        model.addAttribute("genreName", genreName);
        return "genres/slug";
    }

    @GetMapping("/authors/{lastName_firstName}")
    public String authorPage(@PathVariable("lastName_firstName") String authorFullName, Model model) {
        authorService.setModelWithAuthorInfoByAuthorFullName(authorFullName, model);
        return "authors/slug";
    }

    @GetMapping("/books/{authorFullName}")
    public String authorBooksPage(@PathVariable("authorFullName") String authorFullName, Model model) {
        String[] fullName = authorFullName.split("_");
        String lastName = fullName[0];
        String firstName = fullName[1];
        List<Book> bookListWithSpecificAuthor = bookService.receivePageOfBooksWithSpecificAuthor(firstName, lastName, 0, 20).getContent();
        model.addAttribute("bookListWithSpecificAuthor", bookListWithSpecificAuthor);
        model.addAttribute("authorFullName", lastName + " " + firstName);
        model.addAttribute("authorFullNameForUrl", authorFullName);
        return "books/author";
    }
}
