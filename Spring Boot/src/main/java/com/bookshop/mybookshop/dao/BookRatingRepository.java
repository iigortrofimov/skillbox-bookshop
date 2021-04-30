package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.book.BookRating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {

    @Query(value = "SELECT br.book_rate AS rate, COUNT(br.*) AS bookRateCount "
            + "FROM book_rates AS br WHERE br.book_id = ?1 GROUP BY br.book_rate ", nativeQuery = true)
    List<IBookRatingCount> countBookRatesCount(Integer id);

    long countByBookSlug(String slug);
}
