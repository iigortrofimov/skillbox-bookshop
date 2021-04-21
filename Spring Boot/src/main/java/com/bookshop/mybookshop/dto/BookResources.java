package com.bookshop.mybookshop.dto;


import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.domain.book.BookFile;
import com.bookshop.mybookshop.domain.book.BookStatus;
import com.bookshop.mybookshop.domain.book.BookTag;
import com.bookshop.mybookshop.domain.book.Genre;
import com.bookshop.mybookshop.domain.review.Review;
import com.bookshop.mybookshop.domain.user.BalanceTransaction;
import com.bookshop.mybookshop.domain.user.User;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookResources extends RepresentationModel<BookResources> {

    private List<Author> authors = new ArrayList<>();

    private String title;

    private String slug;

    private LocalDateTime publicationDate;

    private Integer discount;

    private Integer price;

    private List<Genre> genres = new ArrayList<>();

    private String description;

    private String image;

    private List<BookTag> bookTags = new ArrayList<>();

    private List<Review> reviews = new ArrayList<>();

    private Boolean isBestseller;

    private List<User> users = new ArrayList<>();

    private List<BookStatus> statuses = new ArrayList<>();

    private List<BalanceTransaction> transactions = new ArrayList<>();

    private Double popularityIndex;

    private List<BookFile> bookFiles = new ArrayList<>();

    public BookResources(List<Author> authors, String title, String slug, LocalDateTime publicationDate,
                         Integer discount, Integer price, List<Genre> genres, String description, String image,
                         List<BookTag> bookTags, List<Review> reviews, Boolean isBestseller, List<User> users,
                         List<BookStatus> statuses, List<BalanceTransaction> transactions, Double popularityIndex,
                         List<BookFile> bookFiles) {
        this.authors = authors;
        this.title = title;
        this.slug = slug;
        this.publicationDate = publicationDate;
        this.discount = discount;
        this.price = price;
        this.genres = genres;
        this.description = description;
        this.image = image;
        this.bookTags = bookTags;
        this.reviews = reviews;
        this.isBestseller = isBestseller;
        this.users = users;
        this.statuses = statuses;
        this.transactions = transactions;
        this.popularityIndex = popularityIndex;
        this.bookFiles = bookFiles;
    }
}
