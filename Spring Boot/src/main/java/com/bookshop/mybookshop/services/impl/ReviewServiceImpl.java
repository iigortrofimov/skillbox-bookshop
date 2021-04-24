package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.dao.BookRepository;
import com.bookshop.mybookshop.dao.ReviewRepository;
import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.review.Review;
import com.bookshop.mybookshop.services.ReviewService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void addNewReview(String slug, String comment) {
        Book book = bookRepository.findBookBySlug(slug);
        Review review = new Review();
        review.setText(comment);
        review.setDateTime(LocalDateTime.now());
        review.setBook(book);
        Review savedReview = reviewRepository.save(review);
        book.getReviews().add(savedReview);
        bookRepository.save(book);
    }
}
