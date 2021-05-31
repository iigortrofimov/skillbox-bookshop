package com.bookshop.mybookshop.domain.user;

import com.bookshop.mybookshop.domain.book.Book;
import com.bookshop.mybookshop.security.BookStoreUser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Entity(name = "balance_transactions")
@Data
@ToString(exclude = {"id", "user", "book"})
public class BalanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BookStoreUser user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '00.00'")
    private Double value;

    @Column(nullable = false)
    private String description;

    public String formattedDateTime() {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return dateTime.format(formatter);
        } else {
            return "undefined";
        }
    }
}
