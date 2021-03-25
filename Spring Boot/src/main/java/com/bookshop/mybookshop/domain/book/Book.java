package com.bookshop.mybookshop.domain.book;

import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.domain.review.Review;
import com.bookshop.mybookshop.domain.user.BalanceTransaction;
import com.bookshop.mybookshop.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "id")
@Entity(name = "books")
@ApiModel(description = "entity representing a book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id generated by DB automatically")
    private Integer id;

    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    private List<Author> authors = new ArrayList<>();

    @Column(nullable = false)
    @ApiModelProperty("book title")
    private String title;

    @Column(nullable = false)
    @ApiModelProperty("mnemonical identity sequence of characters")
    private String slug;

    @Column(name = "pub_date", nullable = false)
    @ApiModelProperty("book publication date")
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    @ColumnDefault("0")
    @ApiModelProperty("book discount in %")
    private Integer discount;

    @Column(nullable = false)
    @ApiModelProperty("book price")
    private Integer price;

    @ManyToMany
    @JoinTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false))
    @ApiModelProperty("book genres")
    private List<Genre> genres = new ArrayList<>();

    @ApiModelProperty("book description")
    private String description;

    @ApiModelProperty("image url")
    private String image;

    @ManyToMany
    @JoinTable(name = "books_tags",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false))
    @ApiModelProperty("list of book' stags")
    private List<BookTag> bookTags = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @ApiModelProperty("book reviews")
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "is_bestseller")
    @ApiModelProperty("is bestseller or not (boolean)")
    private Boolean isBestseller;

    @ManyToMany
    @JoinTable(
            name = "books_users",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @ApiModelProperty("list of users who manipulated with this book")
    private List<User> users = new ArrayList<>();


    @CollectionTable(name = "books_statuses",
            joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = BookStatus.class)
    @ApiModelProperty("book statuses")
    private List<BookStatus> statuses = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @ApiModelProperty("list of transaction with this book")
    private List<BalanceTransaction> transactions = new ArrayList<>();

    @Formula("(select count(bs.book_id) from books_statuses bs WHERE bs.book_id = id AND bs.status = 'PAID') + " +
            "((select count(bs.book_id) from books_statuses bs WHERE bs.book_id = id AND bs.status = 'CART') * 0.7) +" +
            "((select count(bs.book_id) from books_statuses bs WHERE bs.book_id = id AND bs.status = 'ARCHIEVED') * 0.4)")
    private Double popularityIndex;

/*    @Transient
    private double popularityIndex;

    @PostLoad
    private void postLoad() {
        this.popularityIndex =
                statuses.stream().filter(status -> status.equals(BookStatus.PAID)).count() *
                        (statuses.stream().filter(status -> status.equals(BookStatus.CART)).count() * 0.7) *
                        (statuses.stream().filter(status -> status.equals(BookStatus.ARCHIEVED)).count() * 0.4);
    }*/

}