package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.book.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    List<Genre> findByParentIdIsNull();

    List<Genre> findByParentIdIs(Integer parentId);

    List<Genre> findBySlugIs(String genreName);

    Genre findByNameIs(String genreName);

}
