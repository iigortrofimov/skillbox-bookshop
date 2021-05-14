package com.bookshop.mybookshop.domain.review;

import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.security.BookStoreUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;


@ToString(exclude = {"id", "book"})
@Entity(name = "book_reviews")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private BookStoreUser user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String text;

    @OneToMany(mappedBy = "review")
    private List<ReviewLike> likes = new ArrayList<>();

    public String formattedDateTime() {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return dateTime.format(formatter);
        } else {
            return "undefined";
        }
    }

    public Integer likeCount() {
        return (int) likes.stream().filter(reviewLike -> reviewLike.getValue().equals(true)).count();
    }

    public Integer disLikeCount() {
        return (int) likes.stream().filter(reviewLike -> reviewLike.getValue().equals(false)).count();
    }
}
