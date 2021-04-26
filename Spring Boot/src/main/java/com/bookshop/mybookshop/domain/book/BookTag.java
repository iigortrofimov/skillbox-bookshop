package com.bookshop.mybookshop.domain.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Formula;

@Entity(name = "book_tags")
@Data
@ToString(exclude = "books")
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
