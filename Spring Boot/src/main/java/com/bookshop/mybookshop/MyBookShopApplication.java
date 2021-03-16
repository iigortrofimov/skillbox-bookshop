package com.bookshop.mybookshop;

import com.bookshop.mybookshop.dao.AuthorRepository;
import com.bookshop.mybookshop.dao.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class MyBookShopApplication implements CommandLineRunner {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public static void main(String[] args) {
        SpringApplication.run(MyBookShopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

/*        Book book = new Book();
        book.setTitle("TITLE1");
        book.getStatuses().add(BookStatus.PAID);
        book.getStatuses().add(BookStatus.PAID);
        book.getStatuses().add(BookStatus.PAID);
        book.setDiscount(1);
        Book book1 = new Book();
        book1.setTitle("TITLE2");
        book1.getStatuses().add(BookStatus.PAID);
        book1.getStatuses().add(BookStatus.PAID);
        book1.getStatuses().add(BookStatus.ARCHIEVED);
        book1.setDiscount(2);
        Book book2 = new Book();
        book2.setTitle("TITLE3");
        book2.getStatuses().add(BookStatus.PAID);
        book2.getStatuses().add(BookStatus.CART);
        book2.setDiscount(3);

        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);*/


/*        Book book = new Book();

        book.setTitle("TEST");
        book.setDescription("QWERTY QWERTY");
        book.setPrice(100);
        book.setDiscount(200);
        book.setIsBestseller(true);

        Author author = new Author();
        author.setFirstName("FIRST");
        author.setLastName("LAST");
        author.setBiography("BIOGRAPHY !!!");
        authorRepository.save(author);

        book.setAuthor(author);
        book.getBookTags().add(BookTag.LOVE);
        book.getBookTags().add(BookTag.AMERICARN);
        book.getBookTags().add(BookTag.CLASSIC);
        book.setGenre(Genre.LIGHT);

        bookRepository.save(book);

        Book book2 = new Book();

        book2.setTitle("TEST1");
        book2.setDescription("QWERTY QWERTY1");
        book2.setPrice(1001);
        book2.setDiscount(2002);
        book2.setAuthor(author);
        book2.getBookTags().add(BookTag.MAGIC);
        book2.getBookTags().add(BookTag.FAITYTAIL);
        book2.setGenre(Genre.DRAMATURGY);

        bookRepository.save(book2);*/
    }
}
