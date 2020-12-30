package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

public class BookToDelete {

    @Digits(integer = 64, fraction = 0)
    private Integer id;

    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]*")
    private String authorName;

    @Pattern(regexp = "[\\w\\s]*")
    private String title;

    @Digits(integer = 5, fraction = 0)
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
