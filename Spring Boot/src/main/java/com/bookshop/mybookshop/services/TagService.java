package com.bookshop.mybookshop.services;

import com.bookshop.mybookshop.domain.book.BookTag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    List<BookTag> receiveAllBookTags();

    List<BookTag> receiveBookTagsWithXsTag();

    List<BookTag> receiveBookTagsWithSmTag();

    List<BookTag> receiveBookTagsWithTag();

    List<BookTag> receiveBookTagsWithMdTag();

    List<BookTag> receiveBookTagsWithLgTag();

}
