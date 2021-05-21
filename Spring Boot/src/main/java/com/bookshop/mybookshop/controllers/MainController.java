package com.bookshop.mybookshop.controllers;

import com.bookshop.mybookshop.data.ResourceStorage;
import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.BookTag;
import com.bookshop.mybookshop.domain.review.Review;
import com.bookshop.mybookshop.dto.BookReviewRateValue;
import com.bookshop.mybookshop.dto.BooksPageDto;
import com.bookshop.mybookshop.dto.GenreDto;
import com.bookshop.mybookshop.dto.SearchWordDto;
import com.bookshop.mybookshop.exception.EmptySearchException;
import com.bookshop.mybookshop.services.AuthorService;
import com.bookshop.mybookshop.services.BookService;
import com.bookshop.mybookshop.services.GenreService;
import com.bookshop.mybookshop.services.RatingService;
import com.bookshop.mybookshop.services.ReviewService;
import com.bookshop.mybookshop.services.TagService;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
@AllArgsConstructor
@Slf4j
public class MainController {

    private final BookService bookService;
    private final TagService tagService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final ResourceStorage resourceStorage;
    private final ReviewService reviewService;
    private final RatingService ratingService;

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
                               Model model) throws EmptySearchException {
        if (searchWordDto != null) {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults",
                    bookService.receivePageOfSearchResultBooks(searchWordDto.getContent(), 0, 20).getContent());
            return "search/index";
        } else {
            throw new EmptySearchException("Search isn't possible by NULL");
        }
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

    /**
     * Return page with specific book by slug with book in model.
     *
     * @param slug  mnemonical identifier.
     * @param model model.
     * @return page with specific book.
     */
    @GetMapping("/book/{slug}")
    public String bookPage(@PathVariable String slug, Model model) {
        Book book = bookService.receiveBookBySlug(slug);
        model.addAttribute("slugBook", book);
        model.addAttribute("bookRating", ratingService.receiveBookRating(slug));
        return "books/slug";
    }

    /**
     * Receive {@link MultipartFile} file by POST method
     * and saveBook/update that file in specific book by slug.
     *
     * @param file uploaded file.
     * @param slug mnemonical identifier.
     * @return redirect to book page by slug.
     */
    @PostMapping("/book/{slug}/img/save")
    public String saveNewBookImage(@RequestParam MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        String savedPath = resourceStorage.saveNewBookImage(file, slug);
        bookService.updateBookImage(file, savedPath, slug);
        return "redirect:/book/" + slug;
    }

    /**
     * Download book file by hash from path variable using GET method.
     *
     * @param hash book file's hash from URI path variable.
     * @return OK response with book file byte array in body.
     */
    @GetMapping("/book/download/{hash}")
    public ResponseEntity<ByteArrayResource> downloadBookFile(@PathVariable String hash) throws IOException {
        Path bookPath = resourceStorage.receiveBookFilePath(hash);
        log.info("book file path: {}", bookPath);
        MediaType mediaType = resourceStorage.receiveMine(hash);
        log.info("book file mine type: {}", mediaType);
        byte[] data = resourceStorage.receiveBookFileBytes(hash);
        log.info("book file data len: {}", data.length);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + bookPath.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

    /**
     * Receive {@link String} comment by POST method
     * and saveBook that comment in book's {@link Review} by slug.
     *
     * @param comment book's comment.
     * @param slug    mnemonical identifier.
     * @return redirect to book page by slug.
     */
    @PostMapping("/book/{slug}/review/save")
    public String saveNewBookReview(@RequestParam String comment, @RequestParam(required = false) String authorName,
                                    @PathVariable("slug") String slug) {
        reviewService.addNewReview(slug, comment, authorName);
        return "redirect:/book/" + slug;
    }

    @PostMapping("/book/rateBookReview/{bookSlug}")
    public String handleBookReviewRateChanging(@RequestBody BookReviewRateValue reviewRateValue,
                                               @PathVariable("bookSlug") String bookSlug) {
        reviewService.changeBookReviewRate(reviewRateValue.getReviewid(), reviewRateValue.getValue());
        return "redirect:/book/" + bookSlug;
    }
}
