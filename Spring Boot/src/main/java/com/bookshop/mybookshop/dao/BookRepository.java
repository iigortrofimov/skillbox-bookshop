package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.book.Book;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByAuthorsFirstName(String firstName);

    List<Book> findByTitleContaining(String title);

    List<Book> findBooksByPriceBetween(Integer min, Integer max);

    List<Book> findBooksByPriceIs(Integer price);

    List<Book> findByIsBestsellerTrue();

    @Query(value = "select b from books b where b.discount=(SELECT MAX(b.discount) from books b)")
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findByTitleContaining(String title, Pageable nextPage);

    Page<Book> findByIsBestsellerTrue(Pageable nextPage);

    Page<Book> findAllByOrderByPublicationDateDesc(Pageable nextPage);

    Page<Book> findByPublicationDateBetween(LocalDateTime from, LocalDateTime to, Pageable nextPage);

    List<Book> findByOrderByPopularityIndexDesc();

    Page<Book> findByOrderByPopularityIndexDesc(Pageable pageable);

    Page<Book> findByBookTagsSlug(String tagName, Pageable nextPage);

    Page<Book> findByGenresSlug(String genreName, Pageable nextPage);

    List<Book> findByGenresSlug(String genreName);

    Page<Book> findByGenresIdIn(List<Integer> ids, Pageable nextPage);

    Page<Book> findByAuthorsFirstNameAndAuthorsLastName(String firstName, String lastName, Pageable nextPage);

    List<Book> findByAuthorsFirstNameAndAuthorsLastName(String firstName, String lastName);

    Book findBookBySlug(String slug);

    List<Book> findBooksBySlugIn(String[] slugs);

}
