package com.bookshop.mybookshop.services;

public interface ReviewService {
    void addNewReview(String slug, String comment, String authorName);

    void changeBookReviewRate(Integer reviewid, Integer value);
}
