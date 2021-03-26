package com.bookshop.mybookshop.domain.info;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@ToString(exclude = "id")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sort_index", nullable = false)
    @ColumnDefault("0")
    private Integer sortIndex;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;
}
