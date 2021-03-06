package com.bookshop.mybookshop.domain.review;

import com.bookshop.mybookshop.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Entity(name = "review_likes")
@Data
@ToString(exclude = {"id", "user", "review"})
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    @JsonIgnore
    private Review review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "is_bestseller")
    private Boolean value;
}
