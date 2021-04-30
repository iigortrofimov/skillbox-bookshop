package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.BookRatingRepository;
import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.BookRate;
import com.bookshop.mybookshop.domain.book.BookRating;
import com.bookshop.mybookshop.dto.RatingDto;
import com.bookshop.mybookshop.services.RatingService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final BookRepository bookRepository;
    private final BookRatingRepository bookRatingRepository;

    @Override
    public void addRateIntoOverallRating(String slug, Integer value) {
        Book book = bookRepository.findBookBySlug(slug);
        BookRating newRate = new BookRating();
        newRate.setDateTime(LocalDateTime.now());
        newRate.setBook(book);
        switch (value) {
            case (1):
                newRate.setBookRate(BookRate.ONE);
                break;
            case (2):
                newRate.setBookRate(BookRate.TWO);
                break;
            case (3):
                newRate.setBookRate(BookRate.THREE);
                break;
            case (4):
                newRate.setBookRate(BookRate.FOUR);
                break;
            case (5):
                newRate.setBookRate(BookRate.FIVE);
                break;
            default:
                break;
        }
        BookRating savedRating = bookRatingRepository.save(newRate);
        book.getRating().add(savedRating);
        bookRepository.save(book);
    }

    @Override
    public RatingDto receiveBookRating(String slug) {
        RatingDto ratingDto = new RatingDto();
        Book book = bookRepository.findBookBySlug(slug);
        List<BookRating> rating = book.getRating();
        Map<BookRate, List<BookRating>> bookRateListMap = rating.stream().collect(Collectors.groupingBy(BookRating::getBookRate));
        ratingDto.setRatingOverallCount(book.getRating().size());
        if (!bookRateListMap.isEmpty()) {
            ratingDto.setOneStarCount(Optional.ofNullable(bookRateListMap.get(BookRate.ONE))
                    .orElse(new ArrayList<>()).size());
            ratingDto.setTwoStarsCount(Optional.ofNullable(bookRateListMap.get(BookRate.TWO))
                    .orElse(new ArrayList<>()).size());
            ratingDto.setThreeStarsCount(Optional.ofNullable(bookRateListMap.get(BookRate.THREE))
                    .orElse(new ArrayList<>()).size());
            ratingDto.setFourStarsCount(Optional.ofNullable(bookRateListMap.get(BookRate.FOUR))
                    .orElse(new ArrayList<>()).size());
            ratingDto.setFiveStarsCount(Optional.ofNullable(bookRateListMap.get(BookRate.FIVE))
                    .orElse(new ArrayList<>()).size());
        }
        return ratingDto;
    }
}
