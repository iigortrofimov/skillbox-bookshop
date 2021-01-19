package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BookToSave {

    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]*")
    private String authorName;

    @NotBlank
    @Pattern(regexp = "[\\w\\s]*")
    private String title;

    @Digits(integer = 5, fraction = 0)
    private Integer size;

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
