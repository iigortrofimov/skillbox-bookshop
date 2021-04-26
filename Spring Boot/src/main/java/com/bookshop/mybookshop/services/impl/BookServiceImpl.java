package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.dao.GenreRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.Genre;
import com.bookshop.mybookshop.exception.BookstoreApiWrongParameterException;
import com.bookshop.mybookshop.services.BookService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<Book> receiveAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> receiveBooksByAuthorName(String firstName) {
        return bookRepository.findByAuthorsFirstName(firstName);
    }

    @Override
    public List<Book> receiveBooksByTitle(String title) throws BookstoreApiWrongParameterException {
        if (title.equals("") || title.length() <= 1) {
            throw new BookstoreApiWrongParameterException("Wrong value passed to one or more parameters");
        } else {
            List<Book> data = bookRepository.findByTitleContaining(title);
            if (data.size() > 0) {
                return data;
            } else {
                throw new BookstoreApiWrongParameterException("No data with specified parameters.");
            }
        }
    }

    @Override
    public List<Book> receiveBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceBetween(min, max);
    }

    @Override
    public List<Book> receiveBooksByPrice(Integer price) {
        return bookRepository.findBooksByPriceIs(price);
    }

    @Override
    public List<Book> receiveBooksWithMaxDiscount() {
        return bookRepository.getBooksWithMaxDiscount();
    }

    @Override
    public List<Book> receiveBestSellers() {
        return bookRepository.findByIsBestsellerTrue();
    }

    @Override
    public Page<Book> receivePageOfRecommendedBooks(Integer offset, Integer limit) {
        return bookRepository.findAll(PageRequest.of(offset, limit));
    }

    @Override
    public Page<Book> receivePageOfRecentBooks(Integer offset, Integer limit) {
        return bookRepository.findAllByOrderByPublicationDateDesc(PageRequest.of(offset, limit));
    }

    @Override
    public Page<Book> receivePageOfPopularBooks(Integer offset, Integer limit) {
        return bookRepository.findByOrderByPopularityIndexDesc(PageRequest.of(offset, limit));
    }

    @Override
    public Page<Book> receivePageOfSearchResultBooks(String searchTitle, Integer offset, Integer limit) {
        return bookRepository.findByTitleContaining(searchTitle, PageRequest.of(offset, limit));
    }

    @Override
    public Page<Book> receivePageOfRecentBooks(Integer offset, Integer limit, LocalDateTime from, LocalDateTime to) {
        return bookRepository.findByPublicationDateBetween(from, to, PageRequest.of(offset, limit));
    }

    @Override
    public Page<Book> receivePageOfBooksWithSpecificTag(String tagName, Integer offset, Integer limit) {
        return bookRepository.findByBookTagsSlug(tagName, PageRequest.of(offset, limit));
    }

    @Override
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

    @Override
    public Page<Book> receivePageOfBooksWithSpecificAuthor(String firstName, String lastName, Integer offset, Integer limit) {
        return bookRepository.findByAuthorsFirstNameAndAuthorsLastName(firstName, lastName, PageRequest.of(offset, limit));
    }

    @Override
    public List<Book> receiveBooksWithSpecificAuthor(String firstName, String lastName) {
        return bookRepository.findByAuthorsFirstNameAndAuthorsLastName(firstName, lastName);
    }

    @Override
    public Book receiveBookBySlug(String slug) {
        return bookRepository.findBookBySlug(slug);
    }

    @Override
    public void updateBookImage(MultipartFile file, String pathToNewImage, String slug) {
        Book book = bookRepository.findBookBySlug(slug);
        book.setImage(pathToNewImage);
        bookRepository.save(book);
    }

    @Override
    public Book receiveBookById(Integer id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> receiveBooksBySlugIn(String[] slugs) {
        return bookRepository.findBooksBySlugIn(slugs);
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}
