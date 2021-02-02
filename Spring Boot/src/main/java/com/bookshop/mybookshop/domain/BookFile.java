package com.bookshop.mybookshop.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "book_files")
@Data
@ToString(exclude = "id")
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookFileType type;

    @Column(nullable = false)
    private String path;


}
