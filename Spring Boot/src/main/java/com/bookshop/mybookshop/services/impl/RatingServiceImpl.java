package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.book.BookRating;
import com.bookshop.mybookshop.dto.RatingDto;
import com.bookshop.mybookshop.services.RatingService;
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

    @Override
    public void addRateIntoOverallRating(String slug, Integer value) {
        Book book = bookRepository.findBookBySlug(slug);
        List<BookRating> rating = book.getRating();
        switch (value) {
            case (1):
                rating.add(BookRating.ONE);
                break;
            case (2):
                rating.add(BookRating.TWO);
                break;
            case (3):
                rating.add(BookRating.THREE);
                break;
            case (4):
                rating.add(BookRating.FOUR);
                break;
            case (5):
                rating.add(BookRating.FIVE);
                break;
            default:
                break;
        }
        bookRepository.save(book);
    }

    @Override
    public RatingDto receiveBookRating(String slug) {
        RatingDto ratingDto = new RatingDto();
        Book book = bookRepository.findBookBySlug(slug);
        List<BookRating> rating = book.getRating();
        Map<String, List<BookRating>> ratingValueMap = rating.stream().collect(Collectors.groupingBy(BookRating::name));
        ratingDto.setRatingOverallCount(book.getRating().size());
        if (!ratingValueMap.isEmpty()) {
            ratingDto.setOneStarCount(Optional.ofNullable(ratingValueMap.get(BookRating.ONE.name()))
                    .orElse(new ArrayList<>()).size());
            ratingDto.setTwoStarsCount(Optional.ofNullable(ratingValueMap.get(BookRating.TWO.name()))
                    .orElse(new ArrayList<>()).size());
            ratingDto.setThreeStarsCount(Optional.ofNullable(ratingValueMap.get(BookRating.THREE.name()))
                    .orElse(new ArrayList<>()).size());
            ratingDto.setFourStarsCount(Optional.ofNullable(ratingValueMap.get(BookRating.FOUR.name()))
                    .orElse(new ArrayList<>()).size());
            ratingDto.setFiveStarsCount(Optional.ofNullable(ratingValueMap.get(BookRating.FIVE.name()))
                    .orElse(new ArrayList<>()).size());
        }
        return ratingDto;
    }
}
