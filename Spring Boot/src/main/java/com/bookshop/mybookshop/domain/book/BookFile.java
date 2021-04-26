package com.bookshop.mybookshop.domain.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Entity(name = "book_files")
@Data
@ToString(exclude = "id")
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String hash;

    @Column(name = "type_id", nullable = false)
    private Integer typeId;

    @Column(nullable = false)
    private String path;

    /**
     * Book which has this book file;
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    /**
     * Return string with extension of this book
     * by {@code this.typeID} from {@link BookFileType} static method;
     *
     * @return string with extension;
     */
    public String getBookFileExtensionByTypeId() {
        return BookFileType.getExtensionByTypeId(typeId);
    }
}
