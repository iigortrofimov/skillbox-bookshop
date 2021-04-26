package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.dto.RatingDto;

public interface RatingService {

    void addRateIntoOverallRating(String slug, Integer value);

    RatingDto receiveBookRating(String slug);
}
