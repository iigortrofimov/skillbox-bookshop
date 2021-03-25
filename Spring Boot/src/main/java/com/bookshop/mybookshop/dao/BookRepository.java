package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByAuthorsFirstName(String firstName);

    List<Book> findByTitleContaining(String title);

    List<Book> findBooksByPriceBetween(Integer min, Integer max);

    List<Book> findBooksByPriceIs(Integer price);

/*    @Query("from Book where isBestseller = TRUE")
    List<Book> getBestSellers();*/

    List<Book> findByIsBestsellerTrue();

    @Query(value = "select * from books where discount=(SELECT MAX(discount) from books) ", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findByTitleContaining(String title, Pageable nextPage);

    Page<Book> findByIsBestsellerTrue(Pageable nextPage);

    Page<Book> findAllByOrderByPublicationDateDesc(Pageable nextPage);

    Page<Book> findByPublicationDateBetween(LocalDateTime from, LocalDateTime to, Pageable nextPage);

    List<Book> findByOrderByPopularityIndexDesc();

    Page<Book> findByOrderByPopularityIndexDesc(Pageable pageable);

    // Page<Book> findByBookTagsContains(String tagName, Pageable nextPage);
    Page<Book> findByBookTagsSlug(String tagName, Pageable nextPage);

    Page<Book> findByGenresSlug(String genreName, Pageable nextPage);

    List<Book> findByGenresSlug(String genreName);

    Page<Book> findByGenresIdIn(List<Integer> ids, Pageable nextPage);

    Page<Book> findByAuthorsFirstNameAndAuthorsLastName(String firstName, String lastName, Pageable nextPage);

}
