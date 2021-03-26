package com.bookshop.mybookshop.domain.book;

import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "genres")
@Data
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Formula("(select count(bg.book_id) from books_genres bg WHERE bg.genre_id = id)")
    private Integer count;
}
