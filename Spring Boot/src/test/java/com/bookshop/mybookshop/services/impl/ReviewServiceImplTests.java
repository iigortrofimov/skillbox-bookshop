package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.ReviewLikeRepository;
import com.bookshop.mybookshop.dao.ReviewRepository;
import com.bookshop.mybookshop.domain.review.Review;
import com.bookshop.mybookshop.domain.review.ReviewLike;
import com.bookshop.mybookshop.services.ReviewService;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ReviewServiceImplTests {

    @MockBean
    private ReviewRepository reviewRepository;
    @MockBean
    private ReviewLikeRepository reviewLikeRepository;

    private final ReviewService reviewService;

    @Autowired
    ReviewServiceImplTests(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Test
    void changeBookReviewRate() {
        Integer reviewid = 1;
        Integer value = 2;

        Review review = new Review();
        review.setText("text");

        ReviewLike newReviewLike = new ReviewLike();
        newReviewLike.setReview(review);

        Review savedReview = new Review();
        savedReview.setLikes(Collections.singletonList(newReviewLike));

        Mockito.doReturn(Optional.of(review)).when(reviewRepository).findById(reviewid);
        Mockito.doReturn(newReviewLike).when(reviewLikeRepository).save(Mockito.any(ReviewLike.class));
        Mockito.doReturn(savedReview).when(reviewRepository).save(Mockito.any(Review.class));

        Review changedReview = reviewService.changeBookReviewRate(reviewid, value);

        Mockito.verify(reviewRepository, Mockito.times(1)).findById(reviewid);
        Mockito.verify(reviewLikeRepository, Mockito.times(1)).save(Mockito.any(ReviewLike.class));
        Mockito.verify(reviewRepository, Mockito.times(1)).save(Mockito.any(Review.class));

        Assertions.assertEquals(1, changedReview.getLikes().size());
        Assertions.assertTrue(changedReview.getLikes().contains(newReviewLike));
        Assertions.assertEquals("text", changedReview.getLikes().get(0).getReview().getText());
    }
}