package com.bookshop.mybookshop.domain.user;

import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.domain.review.Review;
import com.bookshop.mybookshop.domain.review.ReviewLike;
import com.bookshop.mybookshop.domain.user.contact.UserContact;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Data
@ToString(exclude = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(nullable = false)
    private String hash;

    private String password;

    private String email;

    @Column(nullable = false)
    private LocalDateTime registerDateTime;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer balance;

    @OneToMany(mappedBy = "user")
    private List<ReviewLike> likes = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserContact userContact;

    @ManyToMany(mappedBy = "users")
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BalanceTransaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Message> messages = new ArrayList<>();
}
