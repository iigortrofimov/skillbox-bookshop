package com.bookshop.mybookshop.domain.author;

import com.bookshop.mybookshop.domain.book.Book;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"id", "books"})
@Entity(name = "authors")
@NoArgsConstructor
public class Author extends RepresentationModel<Author> {

    public Author(List<String> authors) {
        if (authors != null) {
            firstName = authors.toString();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "authors_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    private String photo;

    @Column(nullable = false)
    private String slug;

    @Column(length = 4096)
    private String description;
}
