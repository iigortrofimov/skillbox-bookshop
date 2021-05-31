package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.domain.review.Review;
import com.bookshop.mybookshop.domain.user.BalanceTransaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "usr")
@Data
@ToString(exclude = {"id", "reviews", "transactions"})
public class BookStoreUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;

    @Column(columnDefinition = "Decimal(10,2) default '00.00'")
    private Double balance;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<BalanceTransaction> transactions = new ArrayList<>();
}
