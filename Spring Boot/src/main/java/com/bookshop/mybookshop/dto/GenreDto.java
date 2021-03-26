package com.bookshop.mybookshop.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenreDto {

    private String name;
    private String slug;
    private Integer count;
    private List<GenreDto> SubGenres = new ArrayList<>();
}
