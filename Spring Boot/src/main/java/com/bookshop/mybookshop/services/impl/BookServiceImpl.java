package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.dao.GenreRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.Genre;
import com.bookshop.mybookshop.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public List<Book> receiveAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> receiveBooksByAuthorName(String firstName) {
        return bookRepository.findByAuthorsFirstName(firstName);
    }

    public List<Book> receiveBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> receiveBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceBetween(min, max);
    }

    public List<Book> receiveBooksByPrice(Integer price) {
        return bookRepository.findBooksByPriceIs(price);
    }

    public List<Book> receiveBooksWithMaxDiscount() {
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> receiveBestSellers() {
        return bookRepository.findByIsBestsellerTrue();
    }

    public Page<Book> receivePageOfRecommendedBooks(Integer offset, Integer limit) {
        return bookRepository.findAll(PageRequest.of(offset, limit));
    }

    public Page<Book> receivePageOfRecentBooks(Integer offset, Integer limit) {
        return bookRepository.findAllByOrderByPublicationDateDesc(PageRequest.of(offset, limit));
    }

    public Page<Book> receivePageOfPopularBooks(Integer offset, Integer limit) {
        return bookRepository.findByOrderByPopularityIndexDesc(PageRequest.of(offset, limit));
    }

    public Page<Book> receivePageOfSearchResultBooks(String searchTitle, Integer offset, Integer limit) {
        return bookRepository.findByTitleContaining(searchTitle, PageRequest.of(offset, limit));
    }

    public Page<Book> receivePageOfRecentBooks(Integer offset, Integer limit, LocalDateTime from, LocalDateTime to) {
        return bookRepository.findByPublicationDateBetween(from, to, PageRequest.of(offset, limit));
    }

    public Page<Book> receivePageOfBooksWithSpecificTag(String tagName, Integer offset, Integer limit) {
        return bookRepository.findByBookTagsSlug(tagName, PageRequest.of(offset, limit));
    }

/*    public Page<Book> receivePageOfBooksWithSpecificGenre(String genreName, Integer offset, Integer limit) {
        return bookRepository.findByGenresSlug(genreName, PageRequest.of(offset, limit));
    }*/

    public Page<Book> receivePageOfBooksWithSpecificGenre(String genreName, Integer offset, Integer limit) {
        List<Integer> genreIdsList = new ArrayList<>();
        Integer firstLevelGenreId = genreRepository.findByNameIs(genreName).getId();
        genreIdsList.add(firstLevelGenreId);
        List<Integer> secondLevelGenreIdList = genreRepository.findByParentIdIs(firstLevelGenreId)
                .stream()
                .map(Genre::getId)
                .collect(Collectors.toList());
        if (!secondLevelGenreIdList.isEmpty()) {
            genreIdsList.addAll(secondLevelGenreIdList);
            List<Integer> thirdLevelGenreIdList = secondLevelGenreIdList
                    .stream()
                    .map(genreRepository::findByParentIdIs)
                    .flatMap(Collection::stream)
                    .map(Genre::getId)
                    .collect(Collectors.toList());
            genreIdsList.addAll(thirdLevelGenreIdList);
        }
        return bookRepository.findByGenresIdIn(genreIdsList, PageRequest.of(offset, limit));
    }

    public Page<Book> receivePageOfBooksWithSpecificAuthor(String firstName, String lastName, Integer offset, Integer limit) {
        return bookRepository.findByAuthorsFirstNameAndAuthorsLastName(firstName, lastName, PageRequest.of(offset, limit));
    }

}
