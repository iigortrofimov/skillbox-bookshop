package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.dao.ReviewLikeRepository;
import com.bookshop.mybookshop.dao.ReviewRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.review.Review;
import com.bookshop.mybookshop.domain.review.ReviewLike;
import com.bookshop.mybookshop.security.BookStoreUser;
import com.bookshop.mybookshop.security.BookStoreUserRepository;
import com.bookshop.mybookshop.services.ReviewService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final BookStoreUserRepository bookStoreUserRepository;

    @Override
    public void addNewReview(String slug, String comment, String reviewAuthorEmail) {
        Book book = bookRepository.findBookBySlug(slug);
        Review review = new Review();
        review.setText(comment);
        review.setDateTime(LocalDateTime.now());
        review.setBook(book);
        if (reviewAuthorEmail != null) {
            BookStoreUser bookStoreUser = bookStoreUserRepository.findByEmail(reviewAuthorEmail);
            review.setUser(bookStoreUser);
        }
        Review savedReview = reviewRepository.save(review);
        book.getReviews().add(savedReview);
        bookRepository.save(book);
    }

    @Override
    public void changeBookReviewRate(Integer reviewid, Integer value) {
        Review review = reviewRepository.findById(reviewid).get();
        ReviewLike newReviewLike = new ReviewLike();
        if (value == 1) {
            newReviewLike.setValue(true);
        } else if (value == -1) {
            newReviewLike.setValue(false);
        }
        newReviewLike.setDateTime(LocalDateTime.now());
        newReviewLike.setReview(review);
        ReviewLike savedLike = reviewLikeRepository.save(newReviewLike);
        review.getLikes().add(savedLike);
        reviewRepository.save(review);
    }
}
