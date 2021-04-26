package com.bookshop.mybookshop.dto;

import lombok.Data;

@Data
public class RatingDto {

    private String slug;
    private int ratingOverallCount;
    private int oneStarCount;
    private int twoStarsCount;
    private int threeStarsCount;
    private int fourStarsCount;
    private int fiveStarsCount;
}
