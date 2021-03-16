package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<BookTag, Integer> {
}
