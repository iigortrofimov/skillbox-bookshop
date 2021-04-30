package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.review.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Integer> {
}
