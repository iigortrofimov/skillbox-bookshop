package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

public class BookDto {

    //@Pattern(regexp = "(\\d*_\\d*)*", message = "Id isn't correct")
    private Integer id;

    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]*", message = "Error in Author's name")
    private String authorName;

    private String title;

    @Digits(integer = 5, fraction = 0, message = "Only integer")
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
