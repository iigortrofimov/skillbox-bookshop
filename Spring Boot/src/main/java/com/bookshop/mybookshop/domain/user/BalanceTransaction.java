package com.bookshop.mybookshop.domain.user;

import com.bookshop.mybookshop.domain.book.Book;
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
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "balance_transactions")
@Data
@ToString(exclude = {"id", "user", "book"})
public class BalanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer value;

    @Column(nullable = false)
    private String description;
}
