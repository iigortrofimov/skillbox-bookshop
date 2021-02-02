package com.bookshop.mybookshop.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString(exclude = "id")
@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String slug;

    @Column(name = "pub_date", nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer discount;

    @Column(nullable = false)
    private Integer price;

    @CollectionTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Genre.class)
    private List<Genre> genres = new ArrayList<>();

    private String description;

    private Blob image;

    @CollectionTable(name = "books_tags",
            joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "tag", nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = BookTag.class)
    private Set<BookTag> bookTags = new HashSet<>();

    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "is_bestseller")
    private Boolean isBestseller;

    @ManyToMany
    @JoinTable(
            name = "books_users",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @OneToMany(mappedBy = "book")
    private List<BalanceTransaction> transactions = new ArrayList<>();

}
