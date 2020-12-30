package org.example.app.entity;

import org.example.web.dto.BookToSave;

public class Book {
    private Integer id;
    private String author;
    private String title;
    private Integer size;

    public Book(BookToSave bookToSave) {
        this.author = bookToSave.getAuthorName();
        this.title = bookToSave.getTitle();
        this.size = bookToSave.getSize();
    }

/*
    public Book(BookToDelete bookToDelete) {
        this.author = bookToDelete.getAuthorName();
        this.title = bookToDelete.getTitle();
        this.size = bookToDelete.getSize();
    }
*/

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                '}';
    }
}
