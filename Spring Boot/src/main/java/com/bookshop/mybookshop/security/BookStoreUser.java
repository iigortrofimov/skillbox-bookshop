package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.domain.review.Review;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "usr")
@Data
@ToString(exclude = "id")
public class BookStoreUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Provider provider;
}
