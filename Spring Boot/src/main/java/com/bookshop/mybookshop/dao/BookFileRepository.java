package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.book.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFile, Integer> {

    BookFile findBookFileByHash(String hash);
}
