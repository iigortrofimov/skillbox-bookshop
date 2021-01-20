package com.bookshop.mybookshop.domain;

import lombok.Data;

@Data
public class Book {

    private Integer id;
    private Author author;
    private String title;
    private String priceOld;
    private String price;

}
