package com.bookshop.mybookshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "book_tags")
@Data
public class BookTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String slug;

    @ManyToMany(mappedBy = "bookTags")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    @Formula("(select count(bt.book_id) from books_tags bt WHERE bt.tag_id = id)")
    private Integer count;

}
