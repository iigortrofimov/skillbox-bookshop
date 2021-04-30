package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.book.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {
}
