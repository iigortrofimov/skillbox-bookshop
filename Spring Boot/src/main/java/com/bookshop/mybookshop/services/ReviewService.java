package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.review.Review;

public interface ReviewService {
    void addNewReview(String slug, String comment, String authorName);

    Review changeBookReviewRate(Integer reviewid, Integer value);
}
